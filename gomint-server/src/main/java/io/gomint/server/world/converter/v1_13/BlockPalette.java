package io.gomint.server.world.converter.v1_13;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class BlockPalette {
    private String name;
    private List<String> properties = new ArrayList<>();
}
