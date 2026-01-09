# Card Games - Higher/Lower

A simple card game built in Java with a Swing GUI. The main game is Higher/Lower, where you try to guess whether the next card will be higher or lower than the current one.

## How to Run

```bash
mvn compile exec:java
```

Or build a JAR:
```bash
mvn package
java -jar target/hawkeye-coding-task-1.0-SNAPSHOT.jar
```

The main game is ran from CardGameApp.java

## The Game

**Higher/Lower** is pretty straightforward - you see a card, and you guess if the next one will be higher or lower. Get it right, you score a point and keep going. Get it wrong, game over. If the cards are equal, it's a push and you continue without scoring.

There's also a variant with Jokers thrown in, which act as the highest card in the deck.

---

## Design Decisions

### Why MVC?

I went with Model-View-Controller because it keeps things clean and separated. The game logic doesn't know anything about buttons or panels, and the UI doesn't care how shuffling works. This means if I wanted to swap out Swing for JavaFX later (or even make a web version), I'd only need to rewrite the view layer - the models would stay exactly the same.

It also makes the code easier to reason about. When there's a bug in the scoring, I know to look in `HigherLowerModel`. When something looks off visually, it's in the view classes.

### Why Swing over JavaFX?

Honestly? Swing is simpler for a project like this. It comes bundled with the JDK, so there's no extra dependencies to manage. JavaFX would've been fine too, but Swing felt like less overhead for what's essentially a straightforward card game.

The downside is Swing looks a bit dated out of the box, which is why I spent time on custom painting for the cards and buttons. More on that below.

### Custom Card Rendering

I decided to paint the cards myself rather than using images. A few reasons:

1. **No asset management** - Don't need to worry about loading 52+ card images and handling missing files
2. **Scalability** - The cards can resize without getting pixelated
3. **Consistency** - Everything uses the same color scheme and style

The trade-off is that the cards look more "clean and digital" rather than realistic. For a casual game like this, I think that's fine.

### The Listener Pattern

The models use a simple listener/observer pattern to notify views when state changes. When you make a guess, the model updates its state and tells all registered listeners "hey, something changed." The view then pulls the new state and redraws itself.

I could've gone with Java's built-in `PropertyChangeListener` or even a reactive library, but a simple interface felt more appropriate here. Less magic, easier to follow.

### Fisher-Yates Shuffle

For shuffling, I used the Fisher-Yates algorithm rather than just calling `Collections.shuffle()`. Both work fine, but implementing it myself means I understand exactly what's happening - and it's a good algorithm to know. It guarantees a uniform distribution with O(n) complexity.

---

## What Could Be Improved

### Card Animations

Right now, card transitions are instant. It would feel much nicer if cards slid in from the deck, or if there was a brief flip animation when revealing. Swing can do animations, but it requires careful timing with `javax.swing.Timer` and can get fiddly.

### Sound Effects

A subtle card flip sound, a ding for correct guesses, a buzz for wrong ones - these small touches make a big difference in how satisfying a game feels. Java's `javax.sound` API could handle this, I just didn't prioritize it.

### Persistent High Scores

The high score resets when you close the app. It would be nice to save it to a file (or even a simple SQLite database) so your best runs are remembered. Not hard to add, just needs a bit of file I/O.

### Better Card Visuals

The cards look decent, but they're pretty basic. Adding subtle gradients, drop shadows, or even a texture would make them pop more. The Joker especially could use some personality - right now it's just text.

### Keyboard Shortcuts

You can only interact with the mouse currently. Adding keyboard support (H for Higher, L for Lower, N for New Game) would make the game snappier to play.

### Difficulty Modes

Could add an "easy" mode where you see a hint about probability, or a "hard" mode where you have to guess within a time limit. Would add some variety without changing the core game.

### More Games

The architecture supports multiple games - that's why there's a main menu. Adding other card games like Blackjack, War, or simple Poker hands would be straightforward since the Card/Deck models are already built.

### Unit Tests

There are no tests right now, which isn't great. The models are pure logic with no UI dependencies, so they'd be easy to test. Things like "does the deck actually have 52 cards?", "does shuffling produce different orders?", "does scoring work correctly?" - all should be verified.


## Final Thoughts

This was a fun little project. The core game is simple, but there's enough going on with the custom UI and MVC structure to make it interesting. If I were to keep working on it, animations and sound would be my next priorities - they'd really bring the game to life.
