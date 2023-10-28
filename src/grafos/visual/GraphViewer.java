package grafos.visual;

import graphs.AbstractGraph;
import grafos.edges.AbstractEdge;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

public class GraphViewer<V, E extends AbstractEdge<V>> {
    private static final int width = 800;
    private static final int height = 600;

    private final int radius = 14;
    private final AbstractGraph<V, E> graph;
    private final JFrame frame;
    private final GraphPanel panel;

    private final Map<V, Circle> circles = new HashMap<>();
    private final Map<V, List<Line>> adjEdges = new HashMap<>();
    private final Map<V, List<Line>> incEdges = new HashMap<>();
    private final Map<V, JLabel> labels = new HashMap<>();

    private double mouseX;
    private double mouseY;
    private V selectedVertex;
    private Circle selectedCircle;

    public GraphViewer(AbstractGraph<V, E> graph) {
        this.graph = graph;
        this.frame = new JFrame("Graph Viewer");
        this.panel = new GraphPanel();
        this.frame.getContentPane().add(this.panel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
    }

    public void run() {
        if (graph != null) {
            SwingUtilities.invokeLater(() -> {
                frame.setVisible(true);
                drawGraph();
            });
        } else {
            System.out.println("No graph initialized.");
        }
    }

    private void drawGraph() {

        for (V vertex : graph.vertices()) {
            int x = (int) (Math.random() * panel.getWidth());
            int y = (int) (Math.random() * panel.getHeight());
            Circle circle = new Circle(x, y, radius, Color.black);
            panel.add(circle);
            circles.put(vertex, circle);

            JLabel label = new JLabel(vertex.toString());
            label.setBounds((int) circle.getCenterX() - (int)radius/2, (int) circle.getCenterY() - (int)radius/2, (int)radius, (int)radius);
            label.setForeground(Color.WHITE);
            panel.add(label);
            labels.put(vertex, label);

        }

        for (E e : graph.edges()) {
            V v = e.v();
            V w = e.w();

            Circle vCircle = circles.get(v);
            Circle wCircle = circles.get(w);

            Line line = new Line(vCircle.getCenterX(), vCircle.getCenterY(), wCircle.getCenterX(), wCircle.getCenterY());
            panel.add(line);

            adjEdges.computeIfAbsent(v, k -> new ArrayList<>()).add(line);
            incEdges.computeIfAbsent(w, k -> new ArrayList<>()).add(line);
        }

        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
        
                for (V vertex : circles.keySet()) {
                    if (circles.get(vertex).contains(mouseX, mouseY)) {
                        selectedVertex = vertex;
                        selectedCircle = circles.get(vertex);
                        break;
                    }
                }
            }
        
        
            @Override
            public void mouseReleased(MouseEvent e) {
                selectedVertex = null;
                selectedCircle = null;
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedVertex != null) {
                    double newX = e.getX();
                    double newY = e.getY();
        
                    if (newX - radius < 0) {
                        newX = radius;
                    } else if (newX + radius > panel.getWidth()) {
                        newX = panel.getWidth() - radius;
                    }
        
                    if (newY - radius < 0) {
                        newY = radius;
                    } else if (newY + radius > panel.getHeight()) {
                        newY = panel.getHeight() - radius;
                    }

                    double dx = newX - mouseX;
                    double dy = newY - mouseY;
                    
                    mouseX = newX;
                    mouseY = newY;

                    double x = selectedCircle.getCenterX() + dx;
                    double y = selectedCircle.getCenterY() + dy;

                    selectedCircle.setCenterX(x);
                    selectedCircle.setCenterY(y);
                    
                    for (Line line : adjEdges.get(selectedVertex)) {
                        line.setStartX(x);
                        line.setStartY(y);
                    }

                    for (Line line : incEdges.get(selectedVertex)) {
                        line.setEndX(x);
                        line.setEndY(y);
                    }
        
                    JLabel label = labels.get(selectedVertex);
                    
                    label.setBounds((int)x -radius/2, (int)y-radius/2, radius, radius);
                    panel.repaint();
                }
            }
        });

        panel.repaint();
    }

    private static class GraphPanel extends JPanel {
        public GraphPanel() {
            setPreferredSize(new Dimension(width, height));
            setBackground(Color.WHITE);
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Component component : getComponents()) {
                if (component instanceof Drawable)
                    ((Drawable) component).draw(g);
            }
        }
    }

    private interface Drawable {
        void draw(Graphics g);
    }

    private static class Circle extends JComponent implements Drawable {
        private double centerX;
        private double centerY;
        private double radius;
        private Color color;

        public Circle(double centerX, double centerY, double radius, Color color) {
            setBounds((int) (centerX - radius), (int) (centerY - radius), (int) (2 * radius), (int) (2 * radius));
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
            this.color = color;
        }

        public double getCenterX() {
            return centerX;
        }

        public void setCenterX(double centerX) {
            this.centerX = centerX;
            setBounds((int) (centerX - radius), (int) (centerY - radius), (int) (2 * radius), (int) (2 * radius));
        }

        public double getCenterY() {
            return centerY;
        }

        public void setCenterY(double centerY) {
            this.centerY = centerY;
            setBounds((int) (centerX - radius), (int) (centerY - radius), (int) (2 * radius), (int) (2 * radius));
        }

        @Override
        public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.fillOval((int) (centerX - radius), (int) (centerY - radius), (int) (2 * radius), (int) (2 * radius));
        }

        public boolean contains(double x, double y) {
            double dx = centerX - x;
            double dy = centerY - y;
            return dx * dx + dy * dy <= radius * radius;
        }
    }

    private static class Line extends JComponent implements Drawable {
        private double startX;
        private double startY;
        private double endX;
        private double endY;

        public Line(double startX, double startY, double endX, double endY) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }

        public double getStartX() {
            return startX;
        }

        public void setStartX(double startX) {
            this.startX = startX;
        }

        public double getStartY() {
            return startY;
        }

        public void setStartY(double startY) {
            this.startY = startY;
        }

        public double getEndX() {
            return endX;
        }

        public void setEndX(double endX) {
            this.endX = endX;
        }

        public double getEndY() {
            return endY;
        }

        public void setEndY(double endY) {
            this.endY = endY;
        }

        @Override
        public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine((int) startX, (int) startY, (int) endX, (int) endY);
        }
    }
}
