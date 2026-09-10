package com.svi.tictactoewebservice.connection;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.svi.tictactoewebservice.config.ConfigLoader;

/**
 * Owns the {@link Cluster} and {@link Session} objects.
 *
 * <p>Best practice with the DataStax driver: a {@code Cluster}/{@code Session} pair is
 * expensive to create and is thread-safe, so the application should build exactly one
 * of each and reuse it for the lifetime of the JVM, closing it on shutdown.</p>
 */
public final class CassandraConnection implements AutoCloseable {

    private Cluster cluster;
    private Session session;

    /**
     * Explicit lifecycle "init" step — separate from the constructor so this class
     * can be wired the same way you'd wire a context-managed resource elsewhere
     * (e.g. a {@code ServletContextListener.contextInitialized} or a
     * {@code @PostConstruct} method): construct the object cheaply, then call
     * {@code initialize()} to actually open the connection.
     *
     * <p>Builds the {@link Cluster} and opens the {@link Session}. Safe to call only
     * once per instance; call {@link #destroy()} (or {@link #close()}) before
     * re-initializing.</p>
     */

    private CassandraConnection() {
        initialize();
    }

    private static class Holder {
        private static final CassandraConnection INSTANCE =
                new CassandraConnection();
    }

    public static CassandraConnection getInstance() {
        return Holder.INSTANCE;
    }

    public void initialize() {
        if (session != null) {
            throw new IllegalStateException("CassandraConnection is already initialized");
        }

        ConfigLoader config = ConfigLoader.getInstance();

        this.cluster = Cluster.builder()
                .addContactPoint(config.getCassandraIp())
                .withPort(config.getCassandraPort())
                .build();

        this.session = cluster.connect(config.getCassandraKeyspace());
    }

    public Session getSession() {
        if (session == null) {
            throw new IllegalStateException("CassandraConnection has not been initialized. Call initialize() first.");
        }
        return session;
    }

    /**
     * Explicit lifecycle "destroy" step — the counterpart to {@link #initialize()}.
     * Releases the session and shuts down the driver's internal executors/timers.
     * Safe to call multiple times.
     */
    public void destroy() {
        if (session != null && !session.isClosed()) {
            session.close();
        }
        if (cluster != null && !cluster.isClosed()) {
            cluster.close();
        }
    }

    /**
     * Satisfies {@link AutoCloseable} so this class still works with try-with-resources;
     * simply delegates to {@link #destroy()} so there's exactly one teardown path.
     */
    @Override
    public void close() {
        destroy();
    }
}