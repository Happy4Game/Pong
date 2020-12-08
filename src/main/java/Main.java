import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * By Happy4Game
 *
 */

public class Main extends Application {

    private int horizontal;
    private int vertical;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Group root = new Group();
            Scene scene = new Scene(root, 1000, 500);


            Rectangle player1 = new Rectangle(10, 200, 15.0, 80.0);
            Rectangle IA = new Rectangle(975, 200, 15.0, 80.0);

            Text txt = new Text("Perdu !");
            txt.setScaleX(10);
            txt.setScaleY(10);
            txt.setTextOrigin(VPos.CENTER);
            txt.setX(scene.getWidth() / 2);
            txt.setY(scene.getHeight() / 2);
            txt.setFill(Color.RED);
            txt.setVisible(false);

            Circle circle = new Circle(500, 250, 10);
            circle.setFill(Color.BLUE);

            root.getChildren().addAll(circle, player1, IA, txt);

            //If the player push UP key or DOWN key
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.UP && player1.getY() != 0)
                    player1.setY(player1.getY() - 5);

                else if (e.getCode() == KeyCode.DOWN && player1.getY() != 500 - player1.getHeight())
                    player1.setY(player1.getY() + 5);

            });

            //Orientation of the ball
            vertical = -5;
            horizontal = -5;

            //Task repeat
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
                //Collision de la balle sur les extr�mit�s haut et bas
                if (circle.getCenterY() + circle.getRadius() == 500) {
                    vertical = -5;
                } else if (circle.getCenterY() + circle.getRadius() == 0) {
                    vertical = 5;
                }

                //If ball touch player
                if (circle.getCenterX() + circle.getRadius() == player1.getX() +2 * player1.getWidth()) {
                    if (circle.getCenterY() + 25 >= player1.getY() && circle.getCenterY() + 25 <= player1.getY() + player1.getHeight() + 5) {
                        horizontal = 5;
                    }
                }

                //If ball touch player IA
                if (circle.getCenterX() + circle.getRadius() == IA.getX()) {
                    if (circle.getCenterY() + 25 >= IA.getY() && circle.getCenterY() + 25 <= IA.getY() + IA.getHeight()) {
                        horizontal = -5;
                    }

                }
                //If ball don't touch player or IA
                if (circle.getCenterX() + circle.getRadius() == 0 || circle.getCenterX() + circle.getRadius() == scene.getWidth()) {
                    horizontal = 0;
                    vertical = 0;
                    txt.setVisible(true);
                }

                circle.setCenterY(circle.getCenterY() + vertical);
                IA.setY(circle.getCenterY() - IA.getHeight() / 2);
                circle.setCenterX(circle.getCenterX() + horizontal);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);

            primaryStage.setTitle("Projet JavaFX \"Pong\" - Happy4Game");
            primaryStage.setHeight(scene.getHeight());
            primaryStage.setWidth(scene.getWidth());
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
            primaryStage.show();
            Thread.sleep(2000);
            timeline.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
