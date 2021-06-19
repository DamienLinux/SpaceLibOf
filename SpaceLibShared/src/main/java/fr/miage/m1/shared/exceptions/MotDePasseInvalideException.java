/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.m1.shared.exceptions;

/**
 *
 * @author DamienAvetta-Raymond
 */
public class MotDePasseInvalideException extends AuthentificationException {
    public MotDePasseInvalideException(String message) {
        super(message);
    }
}
