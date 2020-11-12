package com.prog.world.UI;

import com.prog.entity.magia.SpellFactory;

public interface UIElement
{
    //la spellfactory serve agli elementi di tipo bar
    SpellFactory sp=SpellFactory.getInstance();

    void draw();

    void update();

    void dispose();
}
