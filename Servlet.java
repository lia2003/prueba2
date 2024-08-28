import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/hangman")
public class HangmanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String[] animales = {"leon", "elefante", "tigre", "cebra", "jirafa", "delfin", "ballena", "gorila", "panda", "aguila", "hipopotamo", "koala", "lobo", "oso", "canguro"};
    private String[] frutas = {"manzana", "platano", "kiwi", "mango", "pera", "uva", "fresa", "naranja", "piña", "sandia", "cereza", "melon", "papaya", "limon", "higo"};
    private String[] paises = {"Mexico", "Canada", "Brasil", "España", "Francia", "Italia", "Alemania", "Japon", "Australia", "Argentina", "Chile", "Peru", "Estados Unidos", "China", "India"};

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if ("start".equals(action)) {
            String theme = request.getParameter("theme");
            int wordLength = Integer.parseInt(request.getParameter("wordLength"));
            int maxAttempts = Integer.parseInt(request.getParameter("maxAttempts"));

            String[] wordList = getWordList(theme);
            String selectedWord = selectRandomWord(wordList, wordLength);

            if (selectedWord == null) {
                session.setAttribute("message", "No hay palabras disponibles para esa longitud.");
                response.sendRedirect("config.jsp");
                return;
            }

            session.setAttribute("selectedWord", selectedWord);
            session.setAttribute("guessedWord", new char[selectedWord.length()]);
            session.setAttribute("attemptsLeft", maxAttempts);
            session.setAttribute("wrongGuesses", new ArrayList<Character>());
            response.sendRedirect("index.jsp");
        } else if ("guess".equals(action)) {
            char guess = request.getParameter("guess").charAt(0);
            processGuess(session, guess);
            response.sendRedirect("index.jsp");
        }
    }

    private String[] getWordList(String theme) {
        switch (theme.toLowerCase()) {
            case "animales":
                return animales;
            case "frutas":
                return frutas;
            case "paises":
                return paises;
            default:
                return new String[]{};
        }
    }

    private String selectRandomWord(String[] wordList, int wordLength) {
        ArrayList<String> filteredWords = new ArrayList<>();
        for (String word : wordList) {
            if (word.length() == wordLength) {
                filteredWords.add(word);
            }
        }
        if (filteredWords.size() == 0) {
            return null;
        }
        Random rand = new Random();
        return filteredWords.get(rand.nextInt(filteredWords.size()));
    }

    private void processGuess(HttpSession session, char guess) {
        String selectedWord = (String) session.getAttribute("selectedWord");
        char[] guessedWord = (char[]) session.getAttribute("guessedWord");
        int attemptsLeft = (int) session.getAttribute("attemptsLeft");
        ArrayList<Character> wrongGuesses = (ArrayList<Character>) session.getAttribute("wrongGuesses");

        boolean correct = false;
        for (int i = 0; i < selectedWord.length(); i++) {
            if (selectedWord.charAt(i) == guess) {
                guessedWord[i] = guess;
                correct = true;
            }
        }
        if (!correct) {
            wrongGuesses.add(guess);
            attemptsLeft--;
        }

        session.setAttribute("guessedWord", guessedWord);
        session.setAttribute("attemptsLeft", attemptsLeft);
        session.setAttribute("wrongGuesses", wrongGuesses);
    }
}
