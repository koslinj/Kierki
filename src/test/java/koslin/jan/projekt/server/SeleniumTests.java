package koslin.jan.projekt.server;

import koslin.jan.projekt.DataType;
import koslin.jan.projekt.Message;
import koslin.jan.projekt.Room;
import koslin.jan.projekt.RoomManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class SeleniumTests {

    private Website website;

    @AfterEach
    public void cleanup() {
        if (website != null) {
            website.stopWebsite();
        }
    }

    @Test
    public void testTitle() throws IOException {
        website = new Website(8080, new RoomManager());
        website.startWebsite();
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080");

        String title = driver.getTitle();
        String expected = "Stan Serwera";
        Assertions.assertEquals(expected, title, "Wrong title of website");
    }

    @Test
    public void testNumberOfDivs1() throws IOException {
        website = new Website(8080, new RoomManager());
        website.startWebsite();
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080");

        WebElement roomInfoDiv = driver.findElement(By.id("roomInfo"));

        // Count the number of div elements inside the "roomInfo" div
        int divCount = roomInfoDiv.findElements(By.tagName("div")).size();
        int expected = 0;
        Assertions.assertEquals(expected, divCount, "Wrong number of player li elements");
    }

    @Test
    public void testNumberOfDivs2() throws IOException {
        Message message = new Message.Builder(DataType.ROOM).roomName("Pokoj1").build();
        RoomManager roomManager = new RoomManager();
        roomManager.addRoom(message);
        website = new Website(8080, roomManager);
        website.startWebsite();
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080");

        WebElement roomInfoDiv = driver.findElement(By.id("roomInfo"));

        // Count the number of div elements inside the "roomInfo" div
        int divCount = roomInfoDiv.findElements(By.tagName("div")).size();
        int expected = 1;
        Assertions.assertEquals(expected, divCount, "Wrong number of player li elements");
    }

    @Test
    public void testNumberOfDivs3() throws IOException {
        RoomManager roomManager = new RoomManager();
        for(int i=1; i<=3; i++){
            Message message = new Message.Builder(DataType.ROOM).roomName("Pokoj" + i).build();
            roomManager.addRoom(message);
        }
        website = new Website(8080, roomManager);
        website.startWebsite();
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080");

        WebElement roomInfoDiv = driver.findElement(By.id("roomInfo"));

        // Count the number of div elements inside the "roomInfo" div
        int divCount = roomInfoDiv.findElements(By.tagName("div")).size();
        int expected = 3;
        Assertions.assertEquals(expected, divCount, "Wrong number of room div elements");
    }

    @Test
    public void testNumberOfPlayers() throws IOException {
        Message message = new Message.Builder(DataType.ROOM).roomName("Pokoj1").build();
        RoomManager roomManager = new RoomManager();
        roomManager.addRoom(message);
        website = new Website(8080, roomManager);
        website.startWebsite();
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080");

        WebElement roomInfoDiv = driver.findElement(By.id("roomInfo"));

        // Count the number of div elements inside the "roomInfo" div
        int count = roomInfoDiv.findElements(By.id("player-li")).size();
        int expected = 0;
        Assertions.assertEquals(expected, count, "Wrong number of player li elements");
    }

    @Test
    public void testNumberOfPlayers2() throws IOException {
        Message message = new Message.Builder(DataType.ROOM).roomName("Pokoj1").build();
        RoomManager roomManager = new RoomManager();
        roomManager.addRoom(message);

        Room room = roomManager.getRooms().get(1);
        room.addPlayer(new Player(0));
        room.addPlayer(new Player(1));

        website = new Website(8080, roomManager);
        website.startWebsite();
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080");

        WebElement roomInfoDiv = driver.findElement(By.id("roomInfo"));

        // Count the number of div elements inside the "roomInfo" div
        int count = roomInfoDiv.findElements(By.id("player-li")).size();
        int expected = 2;
        Assertions.assertEquals(expected, count, "Wrong number of player li elements");
    }

    @Test
    public void testNumberOfPlayers3() throws IOException {
        RoomManager roomManager = new RoomManager();
        for(int i=1; i<=3; i++){
            Message message = new Message.Builder(DataType.ROOM).roomName("Pokoj" + i).build();
            roomManager.addRoom(message);
        }

        Room room1 = roomManager.getRooms().get(1);
        room1.addPlayer(new Player(0));
        room1.addPlayer(new Player(1));

        Room room2 = roomManager.getRooms().get(2);
        room2.addPlayer(new Player(2));
        room2.addPlayer(new Player(3));

        Room room3 = roomManager.getRooms().get(3);
        room3.addPlayer(new Player(4));

        website = new Website(8080, roomManager);
        website.startWebsite();
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:8080");

        WebElement roomInfoDiv = driver.findElement(By.id("roomInfo"));

        // Count the number of div elements inside the "roomInfo" div
        int count = roomInfoDiv.findElements(By.id("player-li")).size();
        int expected = 5;
        Assertions.assertEquals(expected, count, "Wrong number of player li elements");
    }
}
