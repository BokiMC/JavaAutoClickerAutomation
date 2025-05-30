package com.example.eventlogger;


import java.awt.AWTException;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

public class Recorder implements NativeMouseListener, NativeKeyListener {

    private long startTime;
    ArrayList<Object> clickEvents = new ArrayList<>();
    ArrayList<Object> keyboardEvents = new ArrayList<>();

    public void startListening() {
        try {

            startTime = System.currentTimeMillis();
            // Isključi logove
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
            logger.setUseParentHandlers(false);

            // Registruj hook
            GlobalScreen.registerNativeHook();

            // Dodaj listener
            GlobalScreen.addNativeMouseListener(this);
            GlobalScreen.addNativeKeyListener(this);

            System.out.println("Slušanje klikova započeto...");

        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        long clickTime = System.currentTimeMillis();
        long elapsed = clickTime - startTime;
        PointerInfo a = java.awt.MouseInfo.getPointerInfo();
        int x = (int) a.getLocation().getX();
        int y = (int) a.getLocation().getY();
        ClickEvents ClickEvent = new ClickEvents(elapsed, "click",x,y);
        clickEvents.add(ClickEvent);
        System.out.println(ClickEvent);
        System.out.println("Event lista: " + clickEvents);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        long keyPressTime = System.currentTimeMillis();
        long elapsed = keyPressTime - startTime;
        String key = NativeKeyEvent.getKeyText(e.getKeyCode());
        KeyboardEvent keyboardEvent = new KeyboardEvent(elapsed, "keyPressed", key);
        keyboardEvents.add(keyboardEvent);
        System.out.println(keyboardEvent);
        System.out.println("Event lista: " + keyboardEvents);

        if(e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                playback();
                System.out.println("Slušanje klikova završeno.");
                return;
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            } catch (AWTException ex) {
            }
        }
    }

    public void playback() throws NativeHookException, AWTException {
        // Ovdje možete implementirati logiku za reprodukciju događaja
        System.out.println("Replay");
        Timer timer = new Timer();
        Robot robot = new Robot();
        for (Object clickEventObj : clickEvents) {
            if (clickEventObj instanceof ClickEvents) {
                ClickEvents clickEvent = (ClickEvents) clickEventObj;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Replaying click at: " + clickEvent.getX() + ", " + clickEvent.getY());
                        robot.mouseMove((int) clickEvent.getX(), (int) clickEvent.getY());
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }
                }, clickEvent.getElapsedTime());
            }
        }
        for (Object keyboardEventObj : keyboardEvents) {
            if (keyboardEventObj instanceof KeyboardEvent) {
            KeyboardEvent keyboardEvent = (KeyboardEvent) keyboardEventObj;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                System.out.println("Replaying key: " + keyboardEvent.getKey());
                robot.keyPress(getKeyCode(keyboardEvent.getKey()));
                robot.keyRelease(getKeyCode(keyboardEvent.getKey()));
                }
            }, keyboardEvent.getElapsedTime());
            }
        }


        // Oslobađanje resursa
        GlobalScreen.unregisterNativeHook();
        
    }


    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        // Optionally implement if needed
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // Optionally implement if needed
    }

    

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {}

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {}

    // Helper method to map key text to java.awt.event.KeyEvent key codes
    private int getKeyCode(String key) {
        switch (key) {
            // Letters
            case "A": return java.awt.event.KeyEvent.VK_A;
            case "B": return java.awt.event.KeyEvent.VK_B;
            case "C": return java.awt.event.KeyEvent.VK_C;
            case "D": return java.awt.event.KeyEvent.VK_D;
            case "E": return java.awt.event.KeyEvent.VK_E;
            case "F": return java.awt.event.KeyEvent.VK_F;
            case "G": return java.awt.event.KeyEvent.VK_G;
            case "H": return java.awt.event.KeyEvent.VK_H;
            case "I": return java.awt.event.KeyEvent.VK_I;
            case "J": return java.awt.event.KeyEvent.VK_J;
            case "K": return java.awt.event.KeyEvent.VK_K;
            case "L": return java.awt.event.KeyEvent.VK_L;
            case "M": return java.awt.event.KeyEvent.VK_M;
            case "N": return java.awt.event.KeyEvent.VK_N;
            case "O": return java.awt.event.KeyEvent.VK_O;
            case "P": return java.awt.event.KeyEvent.VK_P;
            case "Q": return java.awt.event.KeyEvent.VK_Q;
            case "R": return java.awt.event.KeyEvent.VK_R;
            case "S": return java.awt.event.KeyEvent.VK_S;
            case "T": return java.awt.event.KeyEvent.VK_T;
            case "U": return java.awt.event.KeyEvent.VK_U;
            case "V": return java.awt.event.KeyEvent.VK_V;
            case "W": return java.awt.event.KeyEvent.VK_W;
            case "X": return java.awt.event.KeyEvent.VK_X;
            case "Y": return java.awt.event.KeyEvent.VK_Y;
            case "Z": return java.awt.event.KeyEvent.VK_Z;
            // Numbers
            case "0": return java.awt.event.KeyEvent.VK_0;
            case "1": return java.awt.event.KeyEvent.VK_1;
            case "2": return java.awt.event.KeyEvent.VK_2;
            case "3": return java.awt.event.KeyEvent.VK_3;
            case "4": return java.awt.event.KeyEvent.VK_4;
            case "5": return java.awt.event.KeyEvent.VK_5;
            case "6": return java.awt.event.KeyEvent.VK_6;
            case "7": return java.awt.event.KeyEvent.VK_7;
            case "8": return java.awt.event.KeyEvent.VK_8;
            case "9": return java.awt.event.KeyEvent.VK_9;
            // Function keys
            case "F1": return java.awt.event.KeyEvent.VK_F1;
            case "F2": return java.awt.event.KeyEvent.VK_F2;
            case "F3": return java.awt.event.KeyEvent.VK_F3;
            case "F4": return java.awt.event.KeyEvent.VK_F4;
            case "F5": return java.awt.event.KeyEvent.VK_F5;
            case "F6": return java.awt.event.KeyEvent.VK_F6;
            case "F7": return java.awt.event.KeyEvent.VK_F7;
            case "F8": return java.awt.event.KeyEvent.VK_F8;
            case "F9": return java.awt.event.KeyEvent.VK_F9;
            case "F10": return java.awt.event.KeyEvent.VK_F10;
            case "F11": return java.awt.event.KeyEvent.VK_F11;
            case "F12": return java.awt.event.KeyEvent.VK_F12;
            // Modifier keys
            case "Shift": return java.awt.event.KeyEvent.VK_SHIFT;
            case "Control": return java.awt.event.KeyEvent.VK_CONTROL;
            case "Alt": return java.awt.event.KeyEvent.VK_ALT;
            case "Meta": return java.awt.event.KeyEvent.VK_META;
            case "Alt Graph": return java.awt.event.KeyEvent.VK_ALT_GRAPH;
            // Navigation keys
            case "Up": return java.awt.event.KeyEvent.VK_UP;
            case "Down": return java.awt.event.KeyEvent.VK_DOWN;
            case "Left": return java.awt.event.KeyEvent.VK_LEFT;
            case "Right": return java.awt.event.KeyEvent.VK_RIGHT;
            case "Home": return java.awt.event.KeyEvent.VK_HOME;
            case "End": return java.awt.event.KeyEvent.VK_END;
            case "Page Up": return java.awt.event.KeyEvent.VK_PAGE_UP;
            case "Page Down": return java.awt.event.KeyEvent.VK_PAGE_DOWN;
            case "Insert": return java.awt.event.KeyEvent.VK_INSERT;
            case "Delete": return java.awt.event.KeyEvent.VK_DELETE;
            case "Backspace": return java.awt.event.KeyEvent.VK_BACK_SPACE;
            case "Caps Lock": return java.awt.event.KeyEvent.VK_CAPS_LOCK;
            case "Num Lock": return java.awt.event.KeyEvent.VK_NUM_LOCK;
            case "Scroll Lock": return java.awt.event.KeyEvent.VK_SCROLL_LOCK;
            case "Escape": return java.awt.event.KeyEvent.VK_ESCAPE;
            case "Tab": return java.awt.event.KeyEvent.VK_TAB;
            case "Enter": return java.awt.event.KeyEvent.VK_ENTER;
            case "Space": return java.awt.event.KeyEvent.VK_SPACE;
            // Punctuation and symbols
            case "Comma": return java.awt.event.KeyEvent.VK_COMMA;
            case "Period": return java.awt.event.KeyEvent.VK_PERIOD;
            case "Semicolon": return java.awt.event.KeyEvent.VK_SEMICOLON;
            case "Colon": return java.awt.event.KeyEvent.VK_COLON;
            case "Slash": return java.awt.event.KeyEvent.VK_SLASH;
            case "Back Slash": return java.awt.event.KeyEvent.VK_BACK_SLASH;
            case "Open Bracket": return java.awt.event.KeyEvent.VK_OPEN_BRACKET;
            case "Close Bracket": return java.awt.event.KeyEvent.VK_CLOSE_BRACKET;
            case "Minus": return java.awt.event.KeyEvent.VK_MINUS;
            case "Equals": return java.awt.event.KeyEvent.VK_EQUALS;
            case "Quote": return java.awt.event.KeyEvent.VK_QUOTE;
            case "Back Quote": return java.awt.event.KeyEvent.VK_BACK_QUOTE;
            case "Apostrophe": return java.awt.event.KeyEvent.VK_QUOTE;
            case "At": return java.awt.event.KeyEvent.VK_AT;
            case "Number Sign": return java.awt.event.KeyEvent.VK_NUMBER_SIGN;
            case "Dollar": return java.awt.event.KeyEvent.VK_DOLLAR;
            case "Percent": return java.awt.event.KeyEvent.VK_5; // Shift+5
            case "Ampersand": return java.awt.event.KeyEvent.VK_7; // Shift+7
            case "Asterisk": return java.awt.event.KeyEvent.VK_ASTERISK;
            case "Plus": return java.awt.event.KeyEvent.VK_PLUS;
            case "Underscore": return java.awt.event.KeyEvent.VK_UNDERSCORE;
            case "Less": return java.awt.event.KeyEvent.VK_LESS;
            case "Greater": return java.awt.event.KeyEvent.VK_GREATER;
            case "Exclamation Mark": return java.awt.event.KeyEvent.VK_EXCLAMATION_MARK;
            case "Question": return java.awt.event.KeyEvent.VK_SLASH; // Shift+/
            case "Pipe": return java.awt.event.KeyEvent.VK_BACK_SLASH; // Shift+\
            // Keypad
            case "NumPad 0": return java.awt.event.KeyEvent.VK_NUMPAD0;
            case "NumPad 1": return java.awt.event.KeyEvent.VK_NUMPAD1;
            case "NumPad 2": return java.awt.event.KeyEvent.VK_NUMPAD2;
            case "NumPad 3": return java.awt.event.KeyEvent.VK_NUMPAD3;
            case "NumPad 4": return java.awt.event.KeyEvent.VK_NUMPAD4;
            case "NumPad 5": return java.awt.event.KeyEvent.VK_NUMPAD5;
            case "NumPad 6": return java.awt.event.KeyEvent.VK_NUMPAD6;
            case "NumPad 7": return java.awt.event.KeyEvent.VK_NUMPAD7;
            case "NumPad 8": return java.awt.event.KeyEvent.VK_NUMPAD8;
            case "NumPad 9": return java.awt.event.KeyEvent.VK_NUMPAD9;
            case "NumPad Add": return java.awt.event.KeyEvent.VK_ADD;
            case "NumPad Subtract": return java.awt.event.KeyEvent.VK_SUBTRACT;
            case "NumPad Multiply": return java.awt.event.KeyEvent.VK_MULTIPLY;
            case "NumPad Divide": return java.awt.event.KeyEvent.VK_DIVIDE;
            case "NumPad Decimal": return java.awt.event.KeyEvent.VK_DECIMAL;
            case "NumPad Enter": return java.awt.event.KeyEvent.VK_ENTER;
            // Misc
            case "Print Screen": return java.awt.event.KeyEvent.VK_PRINTSCREEN;
            case "Pause": return java.awt.event.KeyEvent.VK_PAUSE;
            case "Context Menu": return java.awt.event.KeyEvent.VK_CONTEXT_MENU;
            case "Windows": return java.awt.event.KeyEvent.VK_WINDOWS;
            // Media keys (may not be supported on all platforms)
            // Arrows (alternative names)
            case "Arrow Up": return java.awt.event.KeyEvent.VK_UP;
            case "Arrow Down": return java.awt.event.KeyEvent.VK_DOWN;
            case "Arrow Left": return java.awt.event.KeyEvent.VK_LEFT;
            case "Arrow Right": return java.awt.event.KeyEvent.VK_RIGHT;
            default: return java.awt.event.KeyEvent.VK_UNDEFINED;
        }
    }
}

