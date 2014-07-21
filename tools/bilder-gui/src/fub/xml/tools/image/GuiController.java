package fub.xml.tools.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import au.com.bytecode.opencsv.CSVReader;
import fub.xml.tools.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GuiController {
	private static class FileListFactory implements Callback<ListView<File>, ListCell<File>> {

		@Override
		public ListCell<File> call(ListView<File> cell) {
			return new FileNameCell();
		}
		
	}
	
	private static class FileNameCell extends ListCell<File> {
		@Override
        public void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
            	setText(item.getName());
            }
            else {
            	setText("");
            }
        }
	}
	
	private static class RectangleListFactory implements Callback<ListView<Rectangle>, ListCell<Rectangle>> {

		@Override
		public ListCell<Rectangle> call(ListView<Rectangle> cell) {
			return new RectangleCell();
		}
		
	}
	
	private static class RectangleCell extends ListCell<Rectangle> {
		@Override
        public void updateItem(Rectangle item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
            	setText("(" + item.getX() + "|" + item.getY() + ") - ("+(item.getX()+item.getWidth())+"|"+(item.getY()+item.getHeight())+")");
            }
            else {
            	setText("");
            }
        }
	}
	
	@FXML private ListView<File> imageList;
	@FXML private ListView<Rectangle> rectangleList;
	@FXML private ImageView imageView;
	@FXML private Canvas canvas;
	@FXML private MenuItem saveForCurrent;
	@FXML private MenuItem saveForAll;
	@FXML private AnchorPane imageAnchorPane;
	private double dragStartX;
	private double dragStartY;
	private HashMap<File, List<Rectangle>> rectangles = new HashMap<>();
	private File currentFile;
	private ContextMenu deleteCMenu;
	private Rectangle deleteRectangle;
	private double zoomfactor = 1;
	
	@FXML
	public void onMenuOpenPictures(ActionEvent event) {
		if (imageList.getItems().size() != 0) {
			boolean ok = false;
			final Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Label exitLabel = new Label("Sicher?\n\nNicht gespeicherte Änderungen an den aktuellen Bildern gehen verloren!");
            exitLabel.setAlignment(Pos.BASELINE_CENTER);

            Button yesBtn = new Button("Ja");
            yesBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                	imageList.getItems().clear();
                    dialogStage.close();
                }
            });
            Button noBtn = new Button("Nein");

            noBtn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    dialogStage.close();

                }
            });

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.BASELINE_CENTER);
            hBox.setSpacing(40.0);
            hBox.getChildren().addAll(yesBtn, noBtn);

            VBox vBox = new VBox();
            vBox.setSpacing(40.0);
            vBox.getChildren().addAll(exitLabel, hBox);

            dialogStage.setScene(new Scene(vBox));
            dialogStage.showAndWait();
            
            if (imageList.getItems().size() != 0) {
            	return;
            }
		}
		FileChooser filechooser = new FileChooser();
		filechooser.setTitle("Bilder auswählen");
		filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG Bild", "*.jpg"));
		List<File> images = filechooser.showOpenMultipleDialog(Application.getStage());
		
		if (images == null) return;
		
		imageList.getItems().clear();
		imageList.getItems().addAll(images);		
		currentFile = null;
		rectangles.clear();
		
		saveForAll.setDisable(false);
	}
	
	@FXML
	public void onCanvasMouseDragged(MouseEvent event) {
		if (currentFile == null) return;
		drawRectangle(dragStartX, dragStartY, event.getX(), event.getY());
	}
	
	@FXML
	public void onCanvasMousePressed(MouseEvent event) {
		if (currentFile == null) return;
		dragStartX = event.getX();
		dragStartY = event.getY();
	}

	@FXML
	public void onCanvasMouseReleased(MouseEvent event) {
		if (currentFile == null) return;
		double x = event.getX();
		double y = event.getY();

		double rectFromX = dragStartX <= x ? dragStartX : x;
		double rectToX = dragStartX > x ? dragStartX : x;
		double rectFromY = dragStartY <= y ? dragStartY : y;
		double rectToY = dragStartY > y ? dragStartY : y;
		Rectangle rectangle = new Rectangle(rectFromX/zoomfactor, rectFromY/zoomfactor, rectToX/zoomfactor-rectFromX/zoomfactor, rectToY/zoomfactor-rectFromY/zoomfactor);
		
		dragStartX = 0;
		dragStartY = 0;
		
		if (currentFile == null) return;
		if (!rectangles.containsKey(currentFile)) {
			rectangles.put(currentFile, new ArrayList<Rectangle>());
		}
		
		rectangles.get(currentFile).add(rectangle);
		rectangleList.getItems().add(rectangle);
		

		drawRectangles();
	}
		
	private void drawRectangle(double ax, double ay, double bx, double by) {
		if (currentFile == null) return;
		double x1 = ax <= bx ? ax : bx;
		double x2 = ax <= bx ? bx : ax;
		double y1 = ay <= by ? ay : by;
		double y2 = ay <= by ? by : ay;
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.TRANSPARENT);
		gc.fill();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setStroke(Color.RED);
		gc.strokeRect(x1, y1, x2-x1, y2-y1);
	}
	
	private void drawRectangles() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.TRANSPARENT);
		gc.fill();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setStroke(Color.RED);
		
		if (currentFile != null && rectangles.containsKey(currentFile)) {
			for (Rectangle rect : rectangles.get(currentFile)) {
				gc.strokeRect(rect.getX()*zoomfactor, rect.getY()*zoomfactor, rect.getWidth()*zoomfactor, rect.getHeight()*zoomfactor);
			}
		}
	}
	
	public void onFileSelected(File file) {
		System.out.println(file);
		zoomfactor = 1;
		GraphicsContext gc = canvas.getGraphicsContext2D();
		canvas.setCursor(Cursor.CROSSHAIR);
		gc.setFill(Color.TRANSPARENT);
		gc.fill();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		currentFile = file;
		
		if (file == null) {
			saveForCurrent.setDisable(true);
			imageView.setImage(null);
			rectangleList.getItems().clear();
			return;
		}
		saveForCurrent.setDisable(false);
		Image image = new Image("file:\\"+file.getAbsolutePath());
		try {
			image = new Image(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(image);
		imageView.setImage(image);
		imageView.setFitWidth(500);
		zoomfactor = imageView.getFitWidth()/image.getWidth();
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		imageView.setCache(true);

		System.out.println(image.getWidth());
		System.out.println(image.getHeight());
		canvas.setWidth(500);
		canvas.setHeight(500);
		gc.fill();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		rectangleList.getItems().clear();
		
		if (this.rectangles.containsKey(currentFile)) {
			rectangleList.getItems().addAll(this.rectangles.get(currentFile));
		}
		drawRectangles();
	}
	
	@FXML
	public void onContextMenuRequested(ContextMenuEvent event) {
		Rectangle selected = rectangleList.getSelectionModel().getSelectedItem();
		if (selected == null) return;
		deleteRectangle = selected;
		deleteCMenu.show(Application.getStage(), event.getScreenX(), event.getScreenY());
	}
	
	public void onDeleteMenuItem() {
		rectangleList.getItems().remove(deleteRectangle);
		rectangles.get(currentFile).remove(deleteRectangle);
		if (rectangles.get(currentFile).size() == 0) {
			rectangles.remove(currentFile);
		}
		deleteRectangle = null;
		drawRectangles();
	}
	
	@FXML
	public void initialize() {
		imageList.setCellFactory(new GuiController.FileListFactory());
		rectangleList.setCellFactory(new GuiController.RectangleListFactory());
		
		deleteCMenu = new ContextMenu();
		MenuItem deleteRectangleMenuItem = new MenuItem("Löschen");
		deleteCMenu.getItems().add(deleteRectangleMenuItem);
		
		deleteRectangleMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				onDeleteMenuItem();
			}
		});
		
		this.imageList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<File>() {
			@Override
			public void changed(ObservableValue<? extends File> observable, File oldFile, File newFile) {
				System.out.println("selected");
				onFileSelected(newFile);				
			}
		});
		
		saveForCurrent.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if (currentFile == null) return;
				if (rectangles.get(currentFile) != null) {
					save(currentFile);
				}
			}
		});
		
		saveForAll.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				for(File image : imageList.getItems()) {
					if (rectangles.get(image) != null) {
						save(image);
					}
				}
			}
		});
		
	}
	
	public void save(File imageFile) {
		File saveFile = new File(imageFile.getName().concat(".xml"));
		String flickerid = imageFile.getName().split("_")[0];
		String imageName = "";
		try {
			CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("berlinische-gallerie.csv")));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				if (flickerid.equals(nextLine[13])) {
					imageName = nextLine[12];
				}
			}
			FileOutputStream out = new FileOutputStream(saveFile);
			PrintWriter writer = new PrintWriter(out);
			writer.println("<?xml version=\"1.0\"?>");
			writer.println("<Picture>");
			writer.println("  <FlickerID>"+flickerid+"</FlickerID>");
			writer.println("  <Filename>"+imageName+"</Filename>");
			int sectionid = 1;
			for (Rectangle rect : rectangles.get(imageFile)) {
				writer.println("  <Section id=\""+sectionid+"\">");
				writer.println("    <xValue>"+((int)rect.getX())+"</xValue>");
				writer.println("    <yValue>"+((int)rect.getY())+"</yValue>");
				writer.println("    <Width>"+((int)rect.getWidth())+"</Width>");
				writer.println("    <Height>"+((int)rect.getHeight())+"</Height>");
				writer.println("  </Section>");
				sectionid++;
			}
			writer.println("</Picture>");
			writer.close();
			reader.close();
			System.out.println("Saved image " + imageFile.getName());
			System.out.println("  to: " + saveFile.getAbsolutePath());
			System.out.println("  flickerid: " + flickerid);
			System.out.println("  image name: " + imageName);
			System.out.println("");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
