package com.hukuta94.pathnodebuilder.logic.parser.overwatch.model;

import lombok.Getter;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Describes array type of Overwatch workshop
 * @param <T> element type
 */
public class Array<T>
{
    @Getter
    private final List<T> elements;

    public Array() {
        elements = new ArrayList<>();
    }

    public Array(int initialCapacity) {
        elements = new ArrayList<>(initialCapacity);
    }

    public Array(T... values) {
        elements = new ArrayList<>();
        addAll(values);
    }

    public void add(T value) {
        elements.add(value);
    }

    public void addAll(T... values) {
        elements.addAll(Arrays.asList(values));
    }

    public T get(int index) {
        return elements.get(index);
    }

    public int size() {
        return elements.size();
    }

    @Override
    public String toString()
    {
        int elementsSize = elements.size();

        if (elementsSize == 0) {
            return "Array();";
        }

        StringBuilder builder = new StringBuilder();

        // Begin fill array
        builder.append("Array(\n");

        for ( int i = 0; i < elementsSize; i++)
        {
            T element = elements.get(i);

            builder.append("\t\t\t");

            // Element is inner array
            if (element instanceof Array)
            {
                Array array = (Array) element;
                putInnerArray(builder, array);
                putCharacterAtEndLine(builder, elements, element);
                tryPutNextLineCharacter(builder, i, elementsSize);
                continue;
            }

            // Element is not inner array
            builder.append(element != null ? element.toString() : "False");

            putCharacterAtEndLine(builder, elements, element);
            tryPutNextLineCharacter(builder, i, elementsSize);
        }

        return builder.toString();
    }

    private void putInnerArray(StringBuilder builder, Array<T> array)
    {
        if (array == null) {
            builder.append("False");
        }

        builder.append("Array(");

        List<T> elements = array.getElements();
        for (T element : elements)
        {
            builder.append(element.toString());

            if (elements.indexOf(element) != elements.size() - 1) {
                builder.append(", ");
            }
        }

        builder.append(")");
    }

    private void putCharacterAtEndLine(StringBuilder builder, List<T> elements, T currentElement)
    {
        // Dont put ", " if it is the last element
        if (elements.indexOf(currentElement) != elements.size() - 1) {
            builder.append(", ");
        } else {
            builder.append(");");
        }
    }

    private void tryPutNextLineCharacter(StringBuilder builder, int currentIndex, int arraySize) {
        // Put the next line character if an element is not the last
        if (currentIndex < arraySize - 1) {
            builder.append("\n");
        }
    }
}
