package org.lostmc.abacus;

/**
 * Provides a special exception class for the Parser to throw.
 *
 * @author Feaelin (Iain E. Davis) <iain@ruhlendavis.org>
 */
public class MathException extends ArithmeticException {
    /**
     * Message constructor. Merely calls ArithmeticException constructor with the
     * message.
     *
     * @param message Message for the thrown exception.
     */
    MathException(String message) {
        super(message);
    }
}
