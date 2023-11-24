package com.example.hanghaero.exception;

public class CardNotFoundException extends RuntimeException {
	public CardNotFoundException() {
		super("해당 카드가 존재하지 않습니다.");
	}
}
