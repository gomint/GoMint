/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.generator.vanilla.debug;

/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

import io.gomint.server.util.Pair;
import io.gomint.world.Chunk;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author geNAZt
 * @version 1.0
 */
public class UI {

    private static final int SIZE = 256;

    private Map<Pair<Integer, Integer>, CellPane> panes = new ConcurrentHashMap<>();

    public UI() {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
            } catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex ) {
                ex.printStackTrace();
            }

            JFrame frame = new JFrame( "Testing" );
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            frame.setLayout( new BorderLayout() );
            frame.add( new TestPane() );
            frame.setPreferredSize( new Dimension( 2560, 1080 ) );
            frame.pack();
            frame.setLocationRelativeTo( null );
            frame.setVisible( true );

            this.reset();
        });
    }

    public void loadedChunk(Chunk chunk) {
        EventQueue.invokeLater(() -> {
            int x = ( SIZE / 2 ) + chunk.x();
            int z = ( SIZE / 2 ) + chunk.z();

            CellPane pane = panes.get( new Pair<>( x, z ) );
            if ( pane != null ) {
                pane.setBackground( Color.GREEN );
            }
        });
    }

    public void reset() {
        // Reset UI
        EventQueue.invokeLater(() -> {
            for ( CellPane pane : panes.values() ) {
                pane.setBackground( Color.WHITE );
            }
        });
    }

    public class TestPane extends JPanel {

        private int width = SIZE;
        private int height = SIZE;

        public TestPane() {
            setLayout( new GridBagLayout() );

            GridBagConstraints gbc = new GridBagConstraints();
            for ( int row = 0; row <= this.height; row++ ) {
                for ( int col = 0; col <= this.width; col++ ) {
                    gbc.gridx = col;
                    gbc.gridy = row;

                    CellPane cellPane = new CellPane();
                    Border border = new MatteBorder( 1, 1, ( row == this.height ? 1 : 0 ), ( col == this.width ? 1 : 0 ), Color.GRAY );

                    panes.put( new Pair<>( col, row ), cellPane );

                    cellPane.setBorder( border );
                    this.add( cellPane, gbc );
                }
            }
        }
    }

    public class CellPane extends JPanel {

        public CellPane() {

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension( 16, 16 );
        }
    }

}
