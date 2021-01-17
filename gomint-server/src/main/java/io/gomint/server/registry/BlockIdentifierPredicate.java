/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.registry;

import io.gomint.server.util.BlockIdentifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class BlockIdentifierPredicate implements Predicate<BlockIdentifier> {

    private final String id;
    private Map<String, Predicate<String>> statePredicates;
    private BlockIdentifier identifier;

    public BlockIdentifierPredicate(String id) {
        this.id = id;
    }

    @Override
    public boolean test(BlockIdentifier blockIdentifier) {
        if (blockIdentifier.blockId().equals(this.id)) {
            if (this.statePredicates == null) {
                this.identifier = blockIdentifier;
                return true;
            }

            if (blockIdentifier.states() == null) {
                this.identifier = blockIdentifier;
                return true;
            }

            for (String key : blockIdentifier.states().keySet()) { 
                Predicate<String> predicate = this.statePredicates.get(key);
                if (predicate != null) {
                    if (!predicate.test(String.valueOf(blockIdentifier.states().get(key)))) {
                        return false;
                    }
                }
            }

            this.identifier = blockIdentifier;
            return true;
        }

        return false;
    }

    public BlockIdentifier blockIdentifier() {
        return this.identifier;
    }

    public void keyValues(String key, String[] values) {
        if (this.statePredicates == null) {
            this.statePredicates = new HashMap<>();
        }

        Set<String> allowedValues = new HashSet<>(Arrays.asList(values));
        this.statePredicates.put(key, allowedValues::contains);
    }

    public boolean testsStates() {
        return this.statePredicates != null;
    }

}
