package com.prog.entity;

public class Button extends Entity 
{
    public Button(float x, float y, float width, float height, String userData)
    {
        body = createBody(x, y, width, height, 0, userData, 0, 0, 0);
    }
    
    @Override
    public void update(float delta) {
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void draw() {
    }

    @Override
    public void dispose() {
    }
    
}
