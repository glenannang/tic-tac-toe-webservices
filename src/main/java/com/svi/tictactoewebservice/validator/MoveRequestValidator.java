package com.svi.tictactoewebservice.validator;

import com.svi.tictactoewebservice.dto.request.MoveRequest;


public class MoveRequestValidator {

    private final IdValidator idValidator = new IdValidator();

    public void validate(MoveRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request must not be null.");
        }

        idValidator.validateGameId(request.getGameid());
        idValidator.validatePlayerId(request.getPlayerid());

        validateSymbol(request.getSymbol());
        validateLocation(request.getLocation());
        //validateDateSave(request.getDatesave());
    }

    private void validateSymbol(String symbol) {
        if (!"X".equals(symbol) && !"O".equals(symbol)) {
            throw new IllegalArgumentException("Symbol must be X or O.");
        }
    }

    private void validateLocation(String location){
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location is required.");
        }

        try {
            int value = Integer.parseInt(location);

            if (value < 0 || value > 8) {
                throw new IllegalArgumentException("Location must be between 0 and 8.");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Location must be a number.");
        }

    }

//    private void validateDateSave(String datesave) {
//        if (datesave == null || datesave.trim().isEmpty()) {
//            throw new IllegalArgumentException("Date saved is required.");
//        }
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        try {
//            LocalDateTime.parse(datesave, formatter);
//        } catch (DateTimeParseException e) {
//            throw new IllegalArgumentException("Date saved must follow yyyy-MM-dd HH:mm:ss format.");
//        }
//    }


}