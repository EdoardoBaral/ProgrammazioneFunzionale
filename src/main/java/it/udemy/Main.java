package it.udemy;

import it.udemy.lambda.*;

public class Main
{
	public static void main(String[] args)
	{
		/**
		 * In questo primo esempio, si effettua la stampa di una semplice stringa basandoci su quello che è il classico paradigma di programmazione imperativa in Java.
		 * Dovendo indicare al programma cosa deve fare esattamente passo per passo, viene creata una nuova istanza di HelloWorldImperative e viene chiamato il metodo
		 * sayHelloWorld() implementato nella classe, che restituisce la stringa da stampare
		 */
		HelloWorldInterface traditional = new HelloWorldImperative();
		System.out.println("Esempio in programmazione imperativa tradizionale: "+ traditional.sayHelloWorld());
		
		/**
		 * In questo secondo esempio, invece, sfruttiamo invece una lambda expression e il paradigma della programmazione dichiarativa, ovvero diciamo al programma qual è il
		 * risultato che vogliamo ottenere senza specificare però esattamente come realizzarlo.
		 * Non abbiamo quindi un oggetto specifico che svolge il compito che ci interessa ma una lambda expression.
		 * Viene dichiarata quindi una variabile di tipo HelloWorldInterface a cui viene assegnato il risultato di una lambda; in questo caso, definiamo quindi una funzione anonima
		 * senza argomenti che esegue la sola restituzione di una stringa (avendo un corpo della funzione costituito da una sola istruzione, graffe e return sono facoltativi).
		 */
		HelloWorldInterface lambda = () -> "Hello world";
		System.out.println("Esempio in programmazione dichiarativa: "+ lambda.sayHelloWorld());
		
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Qui abbiamo definito una classe IncrementByFiveImperative contenente un metodo incrementByFive(). Questo metodo, dato un intero passato come argomento, ne restituisce
		 * il valore incrementato di 5. Da notare che l'interfaccia IncrementByFiveInterface viene definita utilizzando l'annotazione @FunctionalInterface di Java e contiene un
		 * unico metodo.
		 */
		IncrementByFiveInterface incImperative = new IncrementByFiveImperative();
		System.out.println("[Imperativa  ] Numero: 2 - Incremento di 5: "+ incImperative.incrementByFive(2));
		
		/**
		 * Volendo realizzare lo stesso risultato con una lambda expression, assegniamo alla variabile di tipo IncrementByFiveInterface un'espressione lambda che, stavolta, richiede
		 * un valore come argomento e restituisce tale valore incrementato di 5. Il compilatore, basandosi sulla definizione del metodo nell'interfaccia, sa che deve aspettarsi un
		 * intero come argomento del metodo.
		 */
		IncrementByFiveInterface incLmabda = (num) -> num + 5;
		System.out.println("[Dichiarativa] Numero: 2 - Incremento di 5: "+ incLmabda.incrementByFive(2));
		
		System.out.println("------------------------------------------------------------");
		
		/**
		 * In questo caso, realizziamo essenzialmente una casistica analoga alla precedente con l'unica differenza che il metodo chiamato presenta più di un argomento.
		 * Abbiamo l'interfaccia funzionale ConcatenateInterface che contiene un metodo concatStrings() il quale, date due stringhe come argomenti, ne restituisce la concatenazione
		 */
		ConcatenateInterface concatImperative = new ConcatenateImperative();
		System.out.println("[Imperativa  ] Stringa a: Ciao - Stringa b: Belli -- Risultato: "+ concatImperative.concatStrings("Ciao", "Belli"));
		
		/**
		 * Nel caso della programmazione dichiarativa, utilizziamo una lambda che accetta più di un valore come argomento. Anche qui, il compilatore, basandosi sulla dichiarazione
		 * del metodo nell'interfaccia funzionale, sa di doversi aspettare due stringhe come argomenti del metodo
		 */
		ConcatenateInterface concatLambda = (a, b) -> a.concat(b);
		System.out.println("[Dichiarativa] Stringa a: Ciao - Stringa b: Belli -- Risultato: "+ concatLambda.concatStrings("Ciao", "Belli"));
		
		System.out.println("------------------------------------------------------------");
	}
}