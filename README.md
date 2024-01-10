# "Kierki" Card Game

This is a personal project for one of my courses in the university. 
The project was developed entirely ***from scratch***, independently and without relying on any courses or tutorials.
It is about ***multiplayer*** card game named "Kierki".
Project is made using ***Client - Server*** architecture
Implemented in Java using JavaFX for the graphical user interface, SceneBuilder for layout design, and Sockets for communication.
The game features a web interface displaying server state, including room information with players inside. 

## How it looks

<img src="https://github.com/koslinj/Kierki/assets/97230028/b1e5e8b9-0de9-46ba-86b9-a2bc9afc6b9f" alt="Login / Register" width="350"/>
<img src="https://github.com/koslinj/Kierki/assets/97230028/e7697ce6-3aed-4ad6-9688-f8c911cb4a17" alt="Available rooms" width="350"/>
<img src="https://github.com/koslinj/Kierki/assets/97230028/ea53798d-39ca-4300-8ca1-0924e607884c" alt="Game screen 1" width="700"/>
<img src="https://github.com/koslinj/Kierki/assets/97230028/910ec75c-9b87-4b89-b960-a09563c44f23" alt="Game screen 2" width="700"/>
<img src="https://github.com/koslinj/Kierki/assets/97230028/1a9d4d0a-ad0e-4f8f-a348-d5a166f156ab" alt="Game screen 3" width="700"/>
<img src="https://github.com/koslinj/Kierki/assets/97230028/3891c99c-50b9-419e-b8da-6b68e7df6bae" alt="Game screen 4" width="700"/>
<img src="https://github.com/koslinj/Kierki/assets/97230028/5eda7b06-547c-4a63-a923-9ce256cffb81" alt="Website" width="500"/>


## Features

- **Card Game Logic:** Implements the rules and logic of the Kierki card game.
- **JavaFX GUI:** Provides a graphical user interface using JavaFX for an interactive gaming experience.
- **SceneBuilder Integration:** Utilizes SceneBuilder for designing and managing the layout of the user interface.
- **Socket Communication:** Uses sockets for communication, enabling multiplayer functionality.
- **Multithreading:** This project employs the concept of running additional threads to facilitate message handling on both the server and client sides. 
Each client is equipped with a Receiver class dedicated to receiving messages from the server. On the server side, 
a ClientThread class is run for each connected client. The ClientHandler is responsible for listening to actions from a specific client 
and forwarding updates to all other clients who should be informed about these changes.
- **Embedded HTTP Server:** Includes an embedded HTTP server for displaying server state on a web interface.
- **Config file .xml:** Uses a config.xml file to simplify process of changing ports for HttpServer and SocketServer.
  Additionally there are rules written in config file so it is also possible to adjust rules in a clear way without changing the code

## Tests
### Selenium
- There are a couple of Selenium tests implemented to ensure that any changes in the server state are accurately reflected on the website.
### Unit tests
The `GameLogic` component of this project undergoes thorough unit testing to ensure its proper functionality. 
Given the inherently random nature of the card game and the multitude of possible game situations, relying on manual testing alone would be impractical. 
Unit tests serve as a crucial tool to systematically validate the correctness of the game logic in various scenarios.

Unit testing provides several benefits :

- **Ensuring Correct Behavior:** Unit tests validate that the game logic behaves as expected under different conditions, including edge cases and unexpected inputs.

- **Detecting Regressions:** As the codebase evolves, unit tests act as a safety net, helping to detect regressions or unintended side effects of code changes.

- **Facilitating Refactoring:** With a comprehensive suite of unit tests, I can refactor code confidently, knowing that the existing functionality remains intact if the tests pass.


The unit tests cover a range of scenarios, including :

1. **Card Dealing:** Tests the proper dealing of cards to players and the game deck.

2. **Player Moves:** Validates the correctness of player moves, ensuring that the game progresses according to the rules.

3. **Scoring Mechanism:** Verifies that the scoring mechanism accurately reflects the outcomes of each round.

## Summary
Throughout my work on this project, I have acquired numerous important skills and honed several that I already possessed.
I have not only achieved a comprehensive understanding of various important skills but have also refined existing ones. 

The significance and efficacy of software testing became evident as I tackled the intricacies of a complex project, emphasizing the need for meticulous testing practices. I worked with Selenium which is powerful tool for website testing. Besides Selenium I practiced writing standard Unit Tests which were crucial in terms of speeding up development process.

Working extensively with Socket-Based communication, I encountered the challenge of managing a substantial volume of diverse messages indicative of complex communication logic. To address this, I implemented specialized Java classes, enhancing abstraction and simplifying the code structure.

JavaFX played a pivotal role in the project, providing an avenue for extensive practice, particularly in handling updates to the scene after receiving state changes. This hands-on experience not only solidified my proficiency in JavaFX but also imparted practical insights into managing dynamic user interfaces effectively.

The project afforded opportunities to practice multithreading, allowing for a deeper understanding of concurrent processes and their applications in software development. 

Additionally, the experience of running a website from Java further enriched my skill set, enabling the swift development of a basic website—particularly significant as website development stands as my primary area of interest. 

The culmination of these experiences has contributed to a well-rounded skill set, making this project a valuable and enriching endeavor.

## Getting Started

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/koslinj/Kierki.git
   cd Kierki
