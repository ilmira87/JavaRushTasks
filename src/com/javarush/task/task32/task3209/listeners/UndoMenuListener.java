package com.javarush.task.task32.task3209.listeners;

import com.javarush.task.task32.task3209.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class UndoMenuListener implements MenuListener {
    private View view;
    private JMenuItem undoMenuItem=new JMenuItem("Отменить");
    private JMenuItem redoMenuItem=new JMenuItem("Вернуть");

    public UndoMenuListener(View view, JMenuItem undoMenuItem,JMenuItem redoMenuItem) {
        this.view = view;
        this.redoMenuItem=redoMenuItem;
        this.undoMenuItem=undoMenuItem;
    }

    @Override
    public void menuSelected(MenuEvent menuEvent) {
        undoMenuItem.setEnabled(view.canUndo());
        redoMenuItem.setEnabled(view.canRedo());
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
