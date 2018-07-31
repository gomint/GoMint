/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.emulator;


import io.gomint.emulator.client.Client;
import io.gomint.server.util.Pair;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author geNAZt
 * @version 1.0
 */
public class UI {

    public static void main( String[] args ) {
        new UI();
    }

    private Map<Pair<Integer, Integer>, CellPane> panes = new ConcurrentHashMap<>();

    public UI() {
        EventQueue.invokeLater( new Runnable() {
            @Override
            public void run() {
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
            }
        } );

        ScheduledExecutorService service = Executors.newScheduledThreadPool( 8 ); // Amount of cores
        PostProcessExecutorService postProcessExecutorService = new PostProcessExecutorService();

        service.execute( () -> {
            while ( true ) {
                Client client = new Client( service, postProcessExecutorService.getExecutor(), xzPair -> {
                    int x = 10 + xzPair.getFirst();
                    int z = 10 + xzPair.getSecond();

                    CellPane pane = panes.get( new Pair<>( x, z ) );
                    if ( pane != null ) {
                        EventQueue.invokeLater( new Runnable() {
                            @Override
                            public void run() {
                                if ( xzPair.getFirst() == 0 && xzPair.getSecond() == 0 ) {
                                    pane.setBackground( Color.RED );
                                } else {
                                    pane.setBackground( Color.GREEN );
                                }
                            }
                        } );
                    }
                } );
                client.connect( "192.168.1.147", 19132 );

                try {
                    Thread.sleep( 2000 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }

                client.disconnect( null ); // Instant disconnect

                // Reset UI
                EventQueue.invokeLater( new Runnable() {
                    @Override
                    public void run() {
                        for ( CellPane pane : panes.values() ) {
                            pane.setBackground( Color.WHITE );
                        }
                    }
                } );

                try {
                    Thread.sleep( 100 );
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        } );
    }

    public class TestPane extends JPanel {

        private int width = 20;
        private int height = 20;

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
