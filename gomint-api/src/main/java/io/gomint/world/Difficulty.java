/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world;

/**
 * @author BlackyPaw
 * @version 1.0
 * @stability 2
 */
public enum Difficulty {

    PEACEFUL( 0 ),
    EASY( 1 ),
    NORMAL( 2 ),
    HARD( 3 );

    private int difficultyDegree;

    Difficulty( int difficultyDegree ) {
        this.difficultyDegree = difficultyDegree;
    }

    /**
     * Gets a difficulty given its degree. Returns the difficulty matching the degree or null if no difficulty is
     * assigned to the degree specified.
     *
     * @param degree The degree to get the difficulty of
     * @return The game difficulty or null if not found
     */
    public static Difficulty valueOf( int degree ) {
        return ( degree == 0 ? PEACEFUL : ( degree == 1 ? EASY : ( degree == 2 ? NORMAL : ( degree == 3 ? HARD : null ) ) ) );
    }

    /**
     * Gets the degree of difficulty used inside binary formats such as the NBT inside any level.dat file to
     * represent the game difficulty as an integer.
     *
     * @return The difficulty's degree
     */
    public int getDifficultyDegree() {
        return this.difficultyDegree;
    }

}
