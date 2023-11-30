package ru.netology.moneytransferservice.exceptions;

public class NoSuchTransactionException extends Exception{

    public NoSuchTransactionException(String message){
        super(message);
    }
}
