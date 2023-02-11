import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
/**
 * @author Tyler Schultz
 * 10/15/22
 * Application will go through a random convex hull step by step
 *
 */

class Point
{
    int x, y;
    Point(int x, int y){
        this.x=x;
        this.y=y;
    }
}
 
public class GrahamsAlgorithim extends Application {
     
    public static int ccw(Point a, Point b, Point c)							//Takes 3 points and returns what the direction is
    {
        int val = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
      
        if (val == 0) return 0;  												//points are collinear
        return (val > 0)? 1: 2; 												//points are either clock or counterclock wise
    }
     
    public static void main(String[] args) {
		Application.launch(args);
	}
    
	Pane pointsPane = new Pane();
	Text text = new Text();
	Button bt = new Button("Continue");
    
														
    public void start(Stage primaryStage) throws Exception {
 
    	Point[] points = new Point[15];											//for this program, we will just set 15 points
    	text.setText("Here is a random convex hull. We will start at the lowest y coordinate\nand go clockwise from there. Lets see how this polygon forms:");
    	text.setLayoutX(1100);
    	text.setLayoutY(300);
    	bt.setLayoutX(1250);
    	bt.setLayoutY(350);
    	bt.setOnAction(new ButtonHandler());
    	
    	pointsPane.getChildren().add(text);
        pointsPane.getChildren().add(bt);
    	
    	for (int i = 0; i < 15; i++)
	   	{
	        int x = (int) (Math.random() * 1000);										//we'll generate a random x and y and print those out
	        int y = (int) (Math.random() * 700);
	        Circle point = new Circle(x,y,2.5);											//we create a circle object to represent a point
	  		pointsPane.getChildren().add(point);
	        points[i] = new Point(x, y);												//we'll add the coordinates to our array
	    }
         
        int n = points.length;															//There must be at least 3 points
        if (n < 3) return;
      
        int l = 0;																		//First we will find the leftmost point
        for (int i = 1; i < n; i++)
            if (points[i].y > points[l].y)
                l = i;																	//whatever i is will be our leftmost point
      
        int p = l,
        	q;
        do																				//We will start from leftmost point(p) and keep moving counterclockwise until we reach the start point again
        {
            hull.add(points[p]);														//points[p] will be our first extreme point
      
            																			//Search for a point 'q' such that it is counterclockwise for
            q = (p + 1) % n;
             
            for (int i = 0; i < n; i++)
            {
            	if (ccw(points[p], points[i], points[q]) == 1)							//update q if i is more clockwise
                   q = i;
            }
      
            p = q;																		//set p as q for the next iteration
      
        } while (p != l);  																//While we don't come to first
        
        Scene scene = new Scene(pointsPane,300,200);
	   	primaryStage.setTitle("Grahams Algorithim");
		primaryStage.setScene(scene);
		primaryStage.show();
        
    }
    
    Vector<Point> hull = new Vector<Point>();
    int i = 0;																	//i and j are iterators for the first and second point, respectively
    int j = 1;
   
public class ButtonHandler implements EventHandler<ActionEvent>{
		
	public void handle(ActionEvent event) {
			
		if(j == 1)
			hull.add(hull.get(0));												//we put the first point on top of the hull so we can easily connect the last and the first point together
			        
		if(j != hull.size())													//if j (the larger point) is still within the hull
			{
				Point p1 = hull.get(i);
			    Point p2 = hull.get(j);
			        		
			    if(i > 0)														//we create another line between the two points in the last loop so we can remove the highlight  by changing it to black
			       {
			        	Point p0 = hull.get(i-1);
			        	Line lastLine = new Line(p0.x, p0.y, p1.x, p1.y);
			        	lastLine.setStroke(Color.BLACK);
			        	pointsPane.getChildren().add(lastLine);
			       }
			        		
			    Line line = new Line(p1.x, p1.y, p2.x, p2.y);					//we create a line between line 1 and line 2
			    pointsPane.getChildren().add(line);								//add it to the application
			    line.setStroke(Color.GOLD);										//highlight
			    text.setText("The coordinates for the first point on the highlighted line: (" + p1.x + ", -" + p1.y + ")\nThe coordinates for the second point on the highlighted line: (" + p2.x + ", -" + p2.y + ")");
			    i++;															//i and j become iterators for the next points
				j++;
			}
		}
	}
}