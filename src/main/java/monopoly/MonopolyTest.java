package monopoly;

/**
 * Team Name: Team 3
 * Team Members: Jimmy, Agnes, Kevin, Latifah
 * Project: Programming Project 4 – Monopoly
 * Kevin : Verification 
 *
 */
public class MonopolyTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("=== Monopoly – Test ===\n");

        section("Player – Starting State");
        test_playerStartsAtGO();
        test_playerStartsNotInJail();
        test_playerStartsWithZeroJailTurns();
        test_playerStartsWithZeroDoublesCount();
        test_playerStartsWithZeroGoojfCards();

        section("Player – move()");
        test_moveBasic();
        test_moveWrapsAt40();
        test_moveFrom39By1LandsOnGO();
        test_moveFrom38By4LandsAt2();

        section("Player – goToJail()");
        test_goToJailSetsPosition10();
        test_goToJailSetsInJailTrue();
        test_goToJailResetsDoublesCount();
        test_goToJailResetsJailTurns();

        section("Player – leaveJail()");
        test_leaveJailClearsInJail();
        test_leaveJailResetsJailTurns();
        test_leaveJailDoesNotMovePlayer();

        section("Player – Setters");
        test_setPosition();
        test_setInJail();
        test_setJailTurns();
        test_setDoublesCount();
        test_setGoojfCards();
        test_setJailCardDeck();

        section("Board – Square Layout");
        test_boardHas40Squares();
        test_square0IsGO();
        test_square10IsJAIL();
        test_square30IsGO_TO_JAIL();
        test_chanceSquaresAt7_22_36();
        test_chestSquaresAt2_17_33();
        test_railroadsAt5_15_25_35();
        test_utilitiesAt12_28();
        test_boardGetJailIndexIs10();
        test_boardSizeIs40();
        test_allSquaresHaveNames();

        section("Board – nextRailroad()");
        test_nextRailroadFrom0Is5();
        test_nextRailroadFrom5Is15();      // on a railroad → next one ahead
        test_nextRailroadFrom15Is25();
        test_nextRailroadFrom25Is35();
        test_nextRailroadFrom36WrapsTo5(); // wraps around board
        test_nextRailroadFrom35WrapsTo5(); // Short Line → wraps to Reading

        section("Board – nextUtility()");
        test_nextUtilityFrom0Is12();
        test_nextUtilityFrom12Is28();      // Electric Co → Water Works
        test_nextUtilityFrom28WrapsTo12(); // Water Works → wraps around
        test_nextUtilityFrom29WrapsTo12(); // just past Water Works

        section("Deck – add, draw, reshuffle");
        test_deckStartsEmpty();
        test_addCardIncreasesSize();
        test_drawReturnsCard();
        test_drawDecreasesSize();
        test_drawFromEmptyReturnsNull();
        test_drawReshufflesWhenEmpty();
        test_reshuffleMovesDiscardToQueue();
        test_returnCardGoesToDiscard();
        test_goojfNotAutoDiscarded();       // GOOJF cards held by player, not discarded

        section("Statistics – recordLanding() and getPercentages()");
        test_recordLandingIncrementsTotalTurns();
        test_recordLandingIncrementsCorrectSquare();
        test_sumOfCountsEqualsTotalTurns();
        test_getPercentagesCorrectValue();
        test_getPercentagesLengthIs40();
        test_getPercentagesWhenZeroTurns(); // should not divide by zero

        section("JailStrategy – Strategy A (Immediate Exit)");
        test_stratA_returnsFlase_playerLeavesJail();
        test_stratA_usesGoojfCardIfAvailable();
        test_stratA_goojfCountDropsAfterUse();

        section("JailStrategy – Strategy B (Try for Doubles)");
        test_stratB_incrementsJailTurnsOnFailure();
        test_stratB_playerStaysInJailOnFailure();
        test_stratB_leavesAfter3FailedAttempts();
        test_stratB_usesGoojfBeforeRolling();

        section("Integration – Full Simulation");
        test_integration_totalMovesMatchesRunSimulation();
        test_integration_sumOfCountsEqualsTotalMoves();
        test_integration_square30HasZeroLandings();   // Go To Jail is never a final landing
        test_integration_jailHasHighLandingRate();    // Jail should be most-landed square
        test_integration_allSquaresLandedOnIn100kTurns();

        printSummary();
    }

    // ─────────────────────────────────────────────────────────────────
    // PLAYER – Starting State
    // ─────────────────────────────────────────────────────────────────

    static void test_playerStartsAtGO() {
        check("playerStartsAtGO", new Player().getPosition() == 0);
    }

    static void test_playerStartsNotInJail() {
        check("playerStartsNotInJail", !new Player().isInJail());
    }

    static void test_playerStartsWithZeroJailTurns() {
        check("playerStartsWithZeroJailTurns", new Player().getJailTurns() == 0);
    }

    static void test_playerStartsWithZeroDoublesCount() {
        check("playerStartsWithZeroDoublesCount", new Player().getDoublesCount() == 0);
    }

    static void test_playerStartsWithZeroGoojfCards() {
        check("playerStartsWithZeroGoojfCards", new Player().getGetOutOfJailCards() == 0);
    }

    // ─────────────────────────────────────────────────────────────────
    // PLAYER – move()
    // ─────────────────────────────────────────────────────────────────

    static void test_moveBasic() {
        Player p = new Player();
        p.move(7);
        check("moveBasic", p.getPosition() == 7);
    }

    static void test_moveWrapsAt40() {
        // 38 + 4 = 42, 42 % 40 = 2
        Player p = new Player();
        p.setPosition(38);
        p.move(4);
        check("moveWrapsAt40", p.getPosition() == 2);
    }

    static void test_moveFrom39By1LandsOnGO() {
        // 39 + 1 = 40, 40 % 40 = 0
        Player p = new Player();
        p.setPosition(39);
        p.move(1);
        check("moveFrom39By1LandsOnGO", p.getPosition() == 0);
    }

    static void test_moveFrom38By4LandsAt2() {
        Player p = new Player();
        p.setPosition(38);
        p.move(4);
        check("moveFrom38By4LandsAt2", p.getPosition() == 2);
    }

    // ─────────────────────────────────────────────────────────────────
    // PLAYER – goToJail()
    // ─────────────────────────────────────────────────────────────────

    static void test_goToJailSetsPosition10() {
        Player p = new Player();
        p.setPosition(25);
        p.goToJail();
        check("goToJailSetsPosition10", p.getPosition() == 10);
    }

    static void test_goToJailSetsInJailTrue() {
        Player p = new Player();
        p.goToJail();
        check("goToJailSetsInJailTrue", p.isInJail());
    }

    static void test_goToJailResetsDoublesCount() {
        Player p = new Player();
        p.setDoublesCount(2);
        p.goToJail();
        check("goToJailResetsDoublesCount", p.getDoublesCount() == 0);
    }

    static void test_goToJailResetsJailTurns() {
        Player p = new Player();
        p.setJailTurns(2);
        p.goToJail();
        check("goToJailResetsJailTurns", p.getJailTurns() == 0);
    }

    // ─────────────────────────────────────────────────────────────────
    // PLAYER – leaveJail()
    // ─────────────────────────────────────────────────────────────────

    static void test_leaveJailClearsInJail() {
        Player p = new Player();
        p.goToJail();
        p.leaveJail();
        check("leaveJailClearsInJail", !p.isInJail());
    }

    static void test_leaveJailResetsJailTurns() {
        Player p = new Player();
        p.goToJail();
        p.setJailTurns(2);
        p.leaveJail();
        check("leaveJailResetsJailTurns", p.getJailTurns() == 0);
    }

    static void test_leaveJailDoesNotMovePlayer() {
        // leaveJail() only clears flags — player stays at position 10 until move() is called
        Player p = new Player();
        p.goToJail();
        p.leaveJail();
        check("leaveJailDoesNotMovePlayer", p.getPosition() == 10);
    }

    // ─────────────────────────────────────────────────────────────────
    // PLAYER – Setters
    // ─────────────────────────────────────────────────────────────────

    static void test_setPosition() {
        Player p = new Player();
        p.setPosition(24);
        check("setPosition", p.getPosition() == 24);
    }

    static void test_setInJail() {
        Player p = new Player();
        p.setInJail(true);
        check("setInJail", p.isInJail());
    }

    static void test_setJailTurns() {
        Player p = new Player();
        p.setJailTurns(3);
        check("setJailTurns", p.getJailTurns() == 3);
    }

    static void test_setDoublesCount() {
        Player p = new Player();
        p.setDoublesCount(2);
        check("setDoublesCount", p.getDoublesCount() == 2);
    }

    static void test_setGoojfCards() {
        Player p = new Player();
        p.setGetOutOfJailCards(1);
        check("setGoojfCards", p.getGetOutOfJailCards() == 1);
    }

    static void test_setJailCardDeck() {
        Player p = new Player();
        Deck d = new Deck();
        p.setJailCardDeck(d);
        check("setJailCardDeck", p.getJailCardDeck() == d);
    }

    // ─────────────────────────────────────────────────────────────────
    // BOARD – Square Layout
    // ─────────────────────────────────────────────────────────────────

    static void test_boardHas40Squares() {
        check("boardHas40Squares", new Board().getSquares().size() == 40);
    }

    static void test_square0IsGO() {
        check("square0IsGO", new Board().getSquare(0).getType() == SquareType.GO);
    }

    static void test_square10IsJAIL() {
        check("square10IsJAIL", new Board().getSquare(10).getType() == SquareType.JAIL);
    }

    static void test_square30IsGO_TO_JAIL() {
        check("square30IsGO_TO_JAIL", new Board().getSquare(30).getType() == SquareType.GO_TO_JAIL);
    }

    static void test_chanceSquaresAt7_22_36() {
        Board b = new Board();
        check("chanceSquaresAt7_22_36",
            b.getSquare(7).getType()  == SquareType.CHANCE &&
            b.getSquare(22).getType() == SquareType.CHANCE &&
            b.getSquare(36).getType() == SquareType.CHANCE);
    }

    static void test_chestSquaresAt2_17_33() {
        Board b = new Board();
        check("chestSquaresAt2_17_33",
            b.getSquare(2).getType()  == SquareType.CHEST &&
            b.getSquare(17).getType() == SquareType.CHEST &&
            b.getSquare(33).getType() == SquareType.CHEST);
    }

    static void test_railroadsAt5_15_25_35() {
        Board b = new Board();
        check("railroadsAt5_15_25_35",
            b.getSquare(5).getType()  == SquareType.RAILROAD &&
            b.getSquare(15).getType() == SquareType.RAILROAD &&
            b.getSquare(25).getType() == SquareType.RAILROAD &&
            b.getSquare(35).getType() == SquareType.RAILROAD);
    }

    static void test_utilitiesAt12_28() {
        Board b = new Board();
        check("utilitiesAt12_28",
            b.getSquare(12).getType() == SquareType.UTILITY &&
            b.getSquare(28).getType() == SquareType.UTILITY);
    }

    static void test_boardGetJailIndexIs10() {
        check("boardGetJailIndexIs10", new Board().getJailIndex() == 10);
    }

    static void test_boardSizeIs40() {
        check("boardSizeIs40", new Board().size() == 40);
    }

    static void test_allSquaresHaveNames() {
        Board b = new Board();
        boolean ok = true;
        for (Square s : b.getSquares()) {
            if (s.getName() == null || s.getName().isBlank()) { ok = false; break; }
        }
        check("allSquaresHaveNames", ok);
    }

    // ─────────────────────────────────────────────────────────────────
    // BOARD – nextRailroad()
    // ─────────────────────────────────────────────────────────────────

    static void test_nextRailroadFrom0Is5() {
        check("nextRailroadFrom0Is5", new Board().nextRailroad(0) == 5);
    }

    static void test_nextRailroadFrom5Is15() {
        // Player is ON Reading Railroad — next railroad ahead is Pennsylvania (15)
        check("nextRailroadFrom5Is15", new Board().nextRailroad(5) == 15);
    }

    static void test_nextRailroadFrom15Is25() {
        check("nextRailroadFrom15Is25", new Board().nextRailroad(15) == 25);
    }

    static void test_nextRailroadFrom25Is35() {
        check("nextRailroadFrom25Is35", new Board().nextRailroad(25) == 35);
    }

    static void test_nextRailroadFrom36WrapsTo5() {
        // Chance at 36 — only railroad ahead wraps around to Reading (5)
        check("nextRailroadFrom36WrapsTo5", new Board().nextRailroad(36) == 5);
    }

    static void test_nextRailroadFrom35WrapsTo5() {
        // Short Line at 35 — next railroad wraps to Reading (5)
        check("nextRailroadFrom35WrapsTo5", new Board().nextRailroad(35) == 5);
    }

    // ─────────────────────────────────────────────────────────────────
    // BOARD – nextUtility()
    // ─────────────────────────────────────────────────────────────────

    static void test_nextUtilityFrom0Is12() {
        check("nextUtilityFrom0Is12", new Board().nextUtility(0) == 12);
    }

    static void test_nextUtilityFrom12Is28() {
        // ON Electric Company — next utility ahead is Water Works (28)
        check("nextUtilityFrom12Is28", new Board().nextUtility(12) == 28);
    }

    static void test_nextUtilityFrom28WrapsTo12() {
        // ON Water Works — wraps back to Electric Company (12)
        check("nextUtilityFrom28WrapsTo12", new Board().nextUtility(28) == 12);
    }

    static void test_nextUtilityFrom29WrapsTo12() {
        // Just past Water Works — wraps to Electric Company (12)
        check("nextUtilityFrom29WrapsTo12", new Board().nextUtility(29) == 12);
    }

    // ─────────────────────────────────────────────────────────────────
    // DECK
    // ─────────────────────────────────────────────────────────────────

    static void test_deckStartsEmpty() {
        check("deckStartsEmpty", new Deck().size() == 0);
    }

    static void test_addCardIncreasesSize() {
        Deck d = new Deck();
        d.addCard(new Card("Test", CardType.NO_MOVEMENT, 0));
        check("addCardIncreasesSize", d.size() == 1);
    }

    static void test_drawReturnsCard() {
        Deck d = new Deck();
        Card c = new Card("Test", CardType.NO_MOVEMENT, 0);
        d.addCard(c);
        check("drawReturnsCard", d.draw() == c);
    }

    static void test_drawDecreasesSize() {
        Deck d = new Deck();
        d.addCard(new Card("A", CardType.NO_MOVEMENT, 0));
        d.addCard(new Card("B", CardType.NO_MOVEMENT, 0));
        d.draw();
        check("drawDecreasesSize", d.size() == 1);
    }

    static void test_drawFromEmptyReturnsNull() {
        // Empty deck with no discard — draw() should return null, not crash
        check("drawFromEmptyReturnsNull", new Deck().draw() == null);
    }

    static void test_drawReshufflesWhenEmpty() {
        // Add a card, draw it (goes to discard), draw again → should reshuffle and return it
        Deck d = new Deck();
        d.addCard(new Card("Test", CardType.NO_MOVEMENT, 0));
        d.draw();                // goes to discard
        Card second = d.draw();  // triggers reshuffle
        check("drawReshufflesWhenEmpty", second != null);
    }

    static void test_reshuffleMovesDiscardToQueue() {
        Deck d = new Deck();
        d.addCard(new Card("Test", CardType.NO_MOVEMENT, 0));
        d.draw(); // goes to discard
        d.reshuffle();
        check("reshuffleMovesDiscardToQueue", d.size() == 1 && d.getDiscard().isEmpty());
    }

    static void test_returnCardGoesToDiscard() {
        Deck d = new Deck();
        Card c = new Card("GOOJF", CardType.GET_OUT_OF_JAIL_FREE, 0);
        d.returnCard(c);
        check("returnCardGoesToDiscard", d.getDiscard().size() == 1);
    }

    static void test_goojfNotAutoDiscarded() {
        // When a GOOJF card is drawn, it stays with the player — should NOT go to discard
        Deck d = new Deck();
        d.addCard(new Card("GOOJF", CardType.GET_OUT_OF_JAIL_FREE, 0));
        d.draw();
        check("goojfNotAutoDiscarded", d.getDiscard().isEmpty());
    }

    // ─────────────────────────────────────────────────────────────────
    // STATISTICS
    // ─────────────────────────────────────────────────────────────────

    static void test_recordLandingIncrementsTotalTurns() {
        Statistics s = new Statistics(40);
        s.recordLanding(0);
        s.recordLanding(10);
        check("recordLandingIncrementsTotalTurns", s.getTotalTurns() == 2);
    }

    static void test_recordLandingIncrementsCorrectSquare() {
        Statistics s = new Statistics(40);
        s.recordLanding(24);
        s.recordLanding(24);
        check("recordLandingIncrementsCorrectSquare", s.getLandCounts()[24] == 2);
    }

    static void test_sumOfCountsEqualsTotalTurns() {
        Statistics s = new Statistics(40);
        s.recordLanding(0);
        s.recordLanding(10);
        s.recordLanding(24);
        long sum = 0;
        for (int c : s.getLandCounts()) sum += c;
        check("sumOfCountsEqualsTotalTurns", sum == s.getTotalTurns());
    }

    static void test_getPercentagesCorrectValue() {
        // Land on square 5 twice out of 4 total → 50%
        Statistics s = new Statistics(40);
        s.recordLanding(5);
        s.recordLanding(5);
        s.recordLanding(0);
        s.recordLanding(10);
        check("getPercentagesCorrectValue", s.getPercentages()[5] == 50.0);
    }

    static void test_getPercentagesLengthIs40() {
        check("getPercentagesLengthIs40", new Statistics(40).getPercentages().length == 40);
    }

    static void test_getPercentagesWhenZeroTurns() {
        // No turns recorded — getPercentages() should return all zeros, not crash
        Statistics s = new Statistics(40);
        double[] pct = s.getPercentages();
        boolean allZero = true;
        for (double d : pct) if (d != 0.0) { allZero = false; break; }
        check("getPercentagesWhenZeroTurns", allZero);
    }

    // ─────────────────────────────────────────────────────────────────
    // JAIL STRATEGY A – Immediate Exit
    // ─────────────────────────────────────────────────────────────────

    static void test_stratA_returnsFlase_playerLeavesJail() {
        // Strategy A should return false (turn continues) and release the player
        MonopolyGame game = makeGame(JailStrategyType.IMMEDIATE_EXIT);
        Player p = game.getPlayer();
        p.goToJail();
        boolean turnOver = game.getStrategy().handleJailTurn(p, game);
        check("stratA_returnsFlase_playerLeavesJail", !turnOver && !p.isInJail());
    }

    static void test_stratA_usesGoojfCardIfAvailable() {
        // Even on Strategy A, if a GOOJF card is held it should be used
        MonopolyGame game = makeGame(JailStrategyType.IMMEDIATE_EXIT);
        Player p = game.getPlayer();
        p.goToJail();
        p.setGetOutOfJailCards(1);
        game.getStrategy().handleJailTurn(p, game);
        check("stratA_usesGoojfCardIfAvailable", !p.isInJail());
    }

    static void test_stratA_goojfCountDropsAfterUse() {
        MonopolyGame game = makeGame(JailStrategyType.IMMEDIATE_EXIT);
        Player p = game.getPlayer();
        p.goToJail();
        p.setGetOutOfJailCards(1);
        game.getStrategy().handleJailTurn(p, game);
        check("stratA_goojfCountDropsAfterUse", p.getGetOutOfJailCards() == 0);
    }

    // ─────────────────────────────────────────────────────────────────
    // JAIL STRATEGY B – Try for Doubles
    // ─────────────────────────────────────────────────────────────────

    static void test_stratB_incrementsJailTurnsOnFailure() {
        // If the player doesn't roll doubles, jailTurns should go up by 1
        // Run many times to get a non-doubles outcome at least once
        MonopolyGame game = makeGame(JailStrategyType.TRY_FOR_DOUBLES);
        Player p = game.getPlayer();
        p.goToJail(); // jailTurns = 0

        // Try up to 100 times to get a failed roll (non-doubles)
        boolean tested = false;
        for (int attempt = 0; attempt < 100; attempt++) {
            p.goToJail(); // reset to jailTurns = 0, position = 10
            int turnsBefore = p.getJailTurns();
            boolean turnOver = game.getStrategy().handleJailTurn(p, game);

            // If turnOver is true and player is still in jail → failed roll, turns incremented
            if (turnOver && p.isInJail()) {
                check("stratB_incrementsJailTurnsOnFailure", p.getJailTurns() == turnsBefore + 1);
                tested = true;
                break;
            }
        }
        if (!tested) {
            // Couldn't get a non-doubles result in 100 tries — statistically nearly impossible
            System.out.println("  SKIP – stratB_incrementsJailTurnsOnFailure (couldn't get non-doubles in 100 tries)");
        }
    }

    static void test_stratB_playerStaysInJailOnFailure() {
        // On a failed roll (non-doubles), player should still be in jail
        MonopolyGame game = makeGame(JailStrategyType.TRY_FOR_DOUBLES);
        Player p = game.getPlayer();

        for (int attempt = 0; attempt < 100; attempt++) {
            p.goToJail();
            boolean turnOver = game.getStrategy().handleJailTurn(p, game);
            if (turnOver && p.isInJail()) {
                check("stratB_playerStaysInJailOnFailure", p.isInJail());
                return;
            }
        }
        System.out.println("  SKIP – stratB_playerStaysInJailOnFailure (couldn't get non-doubles)");
    }

    static void test_stratB_leavesAfter3FailedAttempts() {
        // After 3 failed rolls (jailTurns >= 3), player must leave on next call
        MonopolyGame game = makeGame(JailStrategyType.TRY_FOR_DOUBLES);
        Player p = game.getPlayer();
        p.goToJail();
        p.setJailTurns(3); // simulate 3 failed attempts already done
        boolean turnOver = game.getStrategy().handleJailTurn(p, game);
        check("stratB_leavesAfter3FailedAttempts", !p.isInJail() && !turnOver);
    }

    static void test_stratB_usesGoojfBeforeRolling() {
        // Strategy B should use GOOJF card immediately, same as Strategy A
        MonopolyGame game = makeGame(JailStrategyType.TRY_FOR_DOUBLES);
        Player p = game.getPlayer();
        p.goToJail();
        p.setGetOutOfJailCards(1);
        game.getStrategy().handleJailTurn(p, game);
        check("stratB_usesGoojfBeforeRolling", !p.isInJail() && p.getGetOutOfJailCards() == 0);
    }

    // ─────────────────────────────────────────────────────────────────
    // INTEGRATION – Full Simulation
    // ─────────────────────────────────────────────────────────────────

    static void test_integration_totalMovesMatchesRunSimulation() {
        MonopolyGame game = makeGame(JailStrategyType.IMMEDIATE_EXIT);
        game.runSimulation(1000);
        check("integration_totalMovesMatchesRunSimulation", game.getTotalMoves() == 1000);
    }

    static void test_integration_sumOfCountsEqualsTotalMoves() {
        // Every turn must record exactly one landing — sum of all counts == totalMoves
        MonopolyGame game = makeGame(JailStrategyType.IMMEDIATE_EXIT);
        game.runSimulation(1000);
        long sum = 0;
        for (int c : game.getStats().getLandCounts()) sum += c;
        check("integration_sumOfCountsEqualsTotalMoves", sum == game.getTotalMoves());
    }

    static void test_integration_square30HasZeroLandings() {
        // Go To Jail (index 30) immediately moves the player to Jail —
        // it should never be the final recorded landing
        MonopolyGame game = makeGame(JailStrategyType.IMMEDIATE_EXIT);
        game.runSimulation(100_000);
        check("integration_square30HasZeroLandings",
              game.getStats().getLandCounts()[30] == 0);
    }

    static void test_integration_jailHasHighLandingRate() {
        // Jail (index 10) is consistently the most-landed square in Monopoly
        // Over 100k turns it should be well above the average (2.5%)
        MonopolyGame game = makeGame(JailStrategyType.IMMEDIATE_EXIT);
        game.runSimulation(100_000);
        double jailPct = game.getStats().getPercentages()[10];
        check("integration_jailHasHighLandingRate", jailPct > 4.0);
    }

    static void test_integration_allSquaresLandedOnIn100kTurns() {
        // Every square except Go To Jail (30) should be hit at least once in 100k turns
        MonopolyGame game = makeGame(JailStrategyType.IMMEDIATE_EXIT);
        game.runSimulation(100_000);
        int[] counts = game.getStats().getLandCounts();
        boolean ok = true;
        for (int i = 0; i < 40; i++) {
            if (i == 30) continue; // Go To Jail is never a final landing
            if (counts[i] == 0) { ok = false; break; }
        }
        check("integration_allSquaresLandedOnIn100kTurns", ok);
    }

    // ─────────────────────────────────────────────────────────────────
    // HELPERS
    // ─────────────────────────────────────────────────────────────────

    // Creates a fresh game with decks loaded and a given strategy
    private static MonopolyGame makeGame(JailStrategyType strategyType) {
        MonopolyGame game = new MonopolyGame();
        game.setStrategy(new JailStrategy(strategyType));
        loadChanceDeck(game);
        loadChestDeck(game);
        return game;
    }

    // Loads the standard Chance deck (matches Main.java)
    private static void loadChanceDeck(MonopolyGame game) {
        Deck d = game.getChanceDeck();
        d.addCard(new Card("Advance to GO",                   CardType.MOVE_TO,                   0));
        d.addCard(new Card("Go to Jail",                      CardType.GO_TO_JAIL,                10));
        d.addCard(new Card("Go back 3 spaces",                CardType.MOVE_RELATIVE,             -3));
        d.addCard(new Card("Go to nearest Railroad",          CardType.MOVE_TO_NEAREST_RAILROAD,   0));
        d.addCard(new Card("Go to nearest Railroad",          CardType.MOVE_TO_NEAREST_RAILROAD,   0));
        d.addCard(new Card("Go to nearest Utility",           CardType.MOVE_TO_NEAREST_UTILITY,    0));
        d.addCard(new Card("Advance to Illinois Avenue",      CardType.MOVE_TO,                   24));
        d.addCard(new Card("Advance to St. Charles Place",    CardType.MOVE_TO,                   11));
        d.addCard(new Card("Advance to Boardwalk",            CardType.MOVE_TO,                   39));
        d.addCard(new Card("Get Out of Jail Free",            CardType.GET_OUT_OF_JAIL_FREE,       0));
        for (int i = 0; i < 6; i++)
            d.addCard(new Card("No movement", CardType.NO_MOVEMENT, 0));
    }

    // Loads the standard Community Chest deck (matches Main.java)
    private static void loadChestDeck(MonopolyGame game) {
        Deck d = game.getCcDeck();
        d.addCard(new Card("Advance to GO",    CardType.MOVE_TO,              0));
        d.addCard(new Card("Go to Jail",       CardType.GO_TO_JAIL,          10));
        d.addCard(new Card("Get Out of Jail Free", CardType.GET_OUT_OF_JAIL_FREE, 0));
        for (int i = 0; i < 13; i++)
            d.addCard(new Card("No movement", CardType.NO_MOVEMENT, 0));
    }

    // ─────────────────────────────────────────────────────────────────
    // FRAMEWORK
    // ─────────────────────────────────────────────────────────────────

    static void section(String title) {
        System.out.println("\n── " + title + " ──");
    }

    static void check(String name, boolean ok) {
        if (ok) { System.out.println("  PASS  " + name); passed++; }
        else    { System.out.println("  FAIL  " + name); failed++; }
    }

    static void printSummary() {
        System.out.println("\n========================================");
        System.out.printf(  "  PASSED: %d%n", passed);
        System.out.printf(  "  FAILED: %d%n", failed);
        System.out.println("========================================");
        System.out.println(failed == 0 ? "All tests passed!" : "Some tests failed — see above.");
    }
}
