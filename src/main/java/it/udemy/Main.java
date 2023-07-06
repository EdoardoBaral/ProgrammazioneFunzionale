package it.udemy;

import it.udemy.consumer.Instructor;
import it.udemy.consumer.Instructors;
import it.udemy.lambda.*;

import java.util.List;
import java.util.function.*;

public class Main
{
	public static void main(String[] args)
	{
		introduzione();
		consumer();
		specializzazioniConsumer();
		biConsumer();
		predicate();
		specializzazioniPredicate();
	}
	
	private static void introduzione() {
		System.out.println(">>> INTRODUZIONE ALLA PROGRAMMAZIONE DICHIARATIVA/FUNZIONALE E ALLE LAMBDA EXPRESSIONS <<<\n");
		
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
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static void consumer() {
		System.out.println(">>> I CONSUMER <<<\n");
		
		/**
		 * Consumer è un'interfaccia funzionale di Java che accetta un oggetto generico, in questo caso una stringa.
		 * Abbiamo definito un consumer basato su una lambda expression il cui unico compito è quello di stampare in output un determinato messaggio basato sulla stringa ricevuta come
		 * argomento. Nel nostro main (o comunque nel metodo chiamante che sfrutta il consumer), l'esecuzione delle istruzioni del consumer stesso viene innescata dalla chiamata al
		 * metodo accept(), che chiaramente necessita dell'oggetto da passare come argomento alla lambda, in questo caso una stringa.
		 */
		Consumer<String> c = (x) -> System.out.println("String: "+ x +" - Lenght: "+ x.length());
		c.accept("Stringa di prova");
		System.out.println("------------------------------------------------------------");
		
		/**
		 * In questo decondo esempio, realizziamo un secondo Consumer che, a differenza del caso precedente, esegua più di un'istruzione al suo interno.
		 * A parte questa minima differenza e il tipo di oggetto utilizzato per il Consumer, valgono le medesime considerazioni del caso precedente.
		 */
		Consumer<Integer> d = (x) -> {
			System.out.println("Valore: "+ x);
			System.out.println("Quadrato: "+ x*x);
		};
		d.accept(5);
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Simuliamo l'accesso ad un database per mezzo di una classe Instructors, che contiene un unico metodo getAll() che restituisce una serie di oggetti Instructor,
		 * a simulare una lista di istruttori di diverse materie.
		 * Una volta recuperata la lista degli istruttori, sfruttando i consumer e le interfacce funzionali, proviamo a ciclare sull'intera lista e a stampare i dati
		 * di ogni istruttore.
		 * Definiamo quindi un Consumer, che ha il compito di specificare cosa fare con ogni elemento Instructor della lista (stamparne lo stato per mezzo del metodo toString).
		 * Cicliamo poi sulla lista utilizzando il metodo forEach(). Questo richiede in input un consumer, che contenga le istruzioni da eseguire su ogni elemento della lista, qundi
		 * gli passiamo i.
		 * Per definizione, il metodo forEach() cicla su ogni elemento della lista su cui viene chiamato e chiama il metodo accept() del consumer ricevuto come argomento.
		 */
		List<Instructor> instructorList = Instructors.getAll();
		Consumer<Instructor> c1 = (elem) -> System.out.println(elem);
		instructorList.forEach(c1);
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Esempio analogo al precedente, al posto di stampare tutto lo stato di ogni istruttore, viene stampato solo il nome
		 */
		Consumer<Instructor> c2 = (elem) -> System.out.println(elem.getName());
		instructorList.forEach(c2);
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Qui si cerca di combinare l'uso dei consumer, per poter ciclare sulla lista di istruttori e, per ognuno di essi, stampare prima il nome e poi la lista dei corsi in cui insegna.
		 * Per fare questo, teniamo presente due cose: la prima è che abbiamo già un consumer che permette di stampare il nome di un Instructor mentre la seconda è che invece dobbiamo definire
		 * un consumer che stampi la lista dei corsi di un singolo istruttore.
		 * Partiamo quindi con la definizione di quest'ultimo, seguendo le linee guida degli esempi precedenti, dopodiché cicliamo sulla lista con il forEach() ma, questa volta, non passiamogli
		 * come argomento un "singolo" consumer ma una "concatenazione", ottenuta per mezzo del metodo andThen() della classe Consumer di Java. Questo metodo esegue prima il metodo accept()
		 * del consumer chiamante e subito dopo quello del consumer ricevuto come argomento, concatenandone i risultati
		 */
		Consumer<Instructor> c3 = (elem) -> System.out.println(elem.getCourses());
		instructorList.forEach(c2.andThen(c3));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Proviamo ora a compilcare un po' le cose cercando di ciclare sulla lista degli istruttori e stampare lo stato dei soli istruttori che hanno più di 10 anni di esperienza.
		 * Abbiamo già a disposizione un Consumer che permette di stampare lo stato di un istruttore (c1) quindi è inutile definirne uno identico, ricicliamo quello.
		 * Cicliamo come sempre sulla lista di istruttori con il metodo forEach() in cui definiamo una lambda tale per cui, per ogni elemento della lista, verifichiamo se gli anni di
		 * esperianza sono maggiori di 10 e solo in questo caso stampiamo le informazioni dell'istruttore, altrimenti non si fa nulla. La stampa di ottiene chiamando il metodo accept()
		 * del consumer che stiamo usando.
		 */
		instructorList.forEach(elem -> {
			if(elem.getYearsOfExperience() > 10)
				c1.accept(elem);
		});
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Il seguente è un esempio più complesso rispetto ai precedenti, se non altro per quanto riguarda la comprensione delle azioni compiute dal programma.
		 * Supponiamo di voler ciclare sulla lista degli istruttori e di voler stampare nome e stato dei soli istruttori che hanno più di 5 anni di esperienza e che tengano dei corsi online.
		 * Cicliamo sulla lista con il solito forEach() e definiamo quali operazioni devono essere svolte per ogni elemento tramite una lambda.
		 * Per ogni elemento della lista, se le precedenti condizioni vengono soddisfatte, usiamo il Consumer c2 per stampare il nome dell'istruttore e, mediante il metodo andThen(),
		 * accodiamo subito dopo le operazioni definite dal Consumer c1 per stamparne le informazioni di stato. Il risultato della concatenazione di questi due consumer (anch'esso un
		 * Consumer) viene poi applicato all'elemento della lista che stiamo esaminando.
		 */
		instructorList.forEach(elem -> {
			if(elem.getYearsOfExperience() > 5 && elem.isOnlineCourses())
				c2.andThen(c1).accept(elem);
		});
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static void specializzazioniConsumer() {
		System.out.println(">>> SPECIALIZZAZIONI DEI CONSUMER <<<\n");
		
		/**
		 * Java mette a disposizione deverse specializzazioni dell'interfaccia funzionale Consumer, che possono facilitare lo sviluppo qualora si intenda passare al Consumer uno specifico
		 * tipo di dato. Ad esempio, abbiamo gli IntConsumer, che accettano come tipo generico per il consumer un numero intero Integer. Oppure esistono il LongConsumer e il DoubleConsumer
		 * che, analogamente, accettano rispettivamente un Long o un numero decimale Double.
		 * Utilizzando una di queste specializzazioni del Consumer, il compilatore Java riconosce automaticamente il tipo di valore accettato dal metodo accept() che esegue il comportamento
		 * specificato per il Consumer: ad esempio, passare un numero decimale ad un IntConsumer causerebbe un errore di compilazione perché non corrisponderebbe al tipo di valore accettato
		 * da quello specifico Consumer.
		 */
		IntConsumer intConsumer = (val) -> System.out.println("Esempio IntConsumer - Valore: "+ val +" - Risultato: "+ val * 10);
		intConsumer.accept(3);
		
		LongConsumer longConsumer = (val) -> System.out.println("Esempio LongConsumer - Valore: "+ val +" - Risultato: "+val * 10);
		longConsumer.accept(5L);
		
		DoubleConsumer doubleConsumer = (val) -> System.out.println("Esempio DoubleConsumer - Valore: "+ val +" - Risultato: "+val * 10);
		doubleConsumer.accept(4.5);
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static void biConsumer() {
		System.out.println(">>> BICONSUMER <<<\n");
		
		/**
		 * I BiConsumer sono semplicemente dei consumer che accettano due oggetti generici e non restituiscono nulla.
		 * Negli esempi seguenti tratteremo dei BiConsumer che accettano coppie di interi e stringhe e svolgono operazioni su di essi ma è possibile passare ai BiConsumer qualunque coppia
		 * di oggetti sia necessario.
		 */
		BiConsumer<Integer, Integer> biConInt = (a, b) -> System.out.println("Valore a: "+ a +" - Valore b: "+ b);
		biConInt.accept(2, 3);
		
		BiConsumer<Integer, Integer> biConInt2 = (a, b) -> System.out.println("Valore a: "+ a +" - Valore b: "+ b +" - Somma: "+ (a+b));
		biConInt2.accept(3, 7);
		
		BiConsumer<String, String> biConString = (s1, s2) -> System.out.println("Stringa 1: "+ s1 +" - Stringa 2: "+ s2 +" - Concatenazione: "+ s1.concat(s2));
		biConString.accept("Pinco", "Pallo");
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Supponiamo ora di voler riprendere la lista degli istruttori precedentemente definita e di voler applicare i BiConsumer per stampare nome e sesso di ognuno di essi.
		 * Definiamo quindi un BiConsumer che accetti due stringhe (nome e sesso) e lo inizializziamo con una lambda che, date due stringhe, le stampa in un messaggio.
		 * Cicliamo quindi sulla lista con il solito metodo forEach() e applichiamo il comportamento definito dal BiConsumer su ognuno di essi richiamando il metodo accept()
		 * e passandogli in input i relativi nome e sesso.
		 */
		List<Instructor> instructorList2 = Instructors.getAll();
		BiConsumer<String, String> biConInst1 = (nome, sesso) -> System.out.println("Nome: "+ nome +" - Sesso: "+ sesso);
		instructorList2.forEach(elem -> biConInst1.accept(elem.getName(), elem.getGender()));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Analogamente, definiamo un BiConsumer che permetta di stampare il nome di ogni istruttore e la relativa lista dei corsi, Rispetto al caso precedente, l'unica
		 * cosa che cambia è il secondo oggetto accettato dal consumer, che è una lista di stringhe, ma il funzionamento di base rimane fondamentalmente lo stesso.
		 */
		BiConsumer<String, List<String>> biConInst2 = (nome, corsi) -> System.out.println("Nome: "+ nome +" - Corsi: "+ corsi);
		instructorList2.forEach(elem -> biConInst2.accept(elem.getName(), elem.getCourses()));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Volendo affrontare un caso un po' più complicato, supponiamo di voler stampare nome e sesso dei soli istruttori che insegnano online.
		 * Per fare questo, possiamo riciclare un BiConsumer definito precedentemente dopodiché cicliamo come al solito sulla lista, per cercare gli istruttori che rispettano la
		 * condizione voluta. Dentro il forEach() definiamo quindi una lambda in cui verifichiamo con un if il valore del flag onlineCourses e, nel caso sia true, si chiami
		 * il metodo accept() del BiConsumer utilizzato.
		 */
		instructorList2.forEach(elem -> {
			if(elem.isOnlineCourses())
				biConInst1.accept(elem.getName(), elem.getGender());
		});
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Versione alternativa del test precedente, in cui si utilizzano le Stream API di Java per effettuare le verifiche sugli istruttori della lista, per rendere più compatta
		 * la scrittura del codice.
		 * Si apre uno stream sulla lista degli istruttori e si chiama il metodo filter(). Al metodo filter() viene passata una lambda per verificare il valore del flag onlineCourses
		 * sull'i-esimo elemento della lista. Il metodo filter() quindi restituisce una sottolista che contiene i soli elementi che rispettano la condizione data.
		 * Si cicla sulla sottolista restituita con il metodo forEach() e, per ognuno degli elementi, si chiama il BiConsumer come nell'esempio precedente.
		 */
		instructorList2.stream()
			.filter(elem -> elem.isOnlineCourses())
			.forEach(elem -> biConInst1.accept(elem.getName(), elem.getGender()));
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static void predicate() {
		System.out.println(">>> PREDICATE <<<\n");
		
		/**
		 * Predicate è un'interfaccia funzionale di Java che mette a disposizione un metodo test() che restituisce un valore booleano. Inizializzando un Predicate con una
		 * lambda expression che restituisce un booleano, quando viene eseguito il metodo test(), l'espressione viene valutata e il metodo restituisc il valore corrispondente.
		 */
		Predicate<Integer> p1 = (val) -> val > 10;
		System.out.println("Valore: 15 - Risultato: "+ p1.test(15));
		System.out.println("Valore: 2 - Risultato: "+ p1.test(2));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * L'interfaccia Predicate mette a disposizione anche tutta una serie di metodi che permettono di combinare più Predicate per valutare delle condizioni complesse.
		 * Ad esempio, se volessimo valutare se un numero intero sia maggiore di 10 e sia anche pari, potremmo definire due Predicate che valutino separatamente le due condizioni
		 * per poi combinarli insieme con il metodo and() dell'interfaccia Predicate.
		 * Avendo già definito un Predicate che valuta se un intero sia maggiore di 10, basta definirne un secondo che verifichi se un intero sia pari.
		 * Andremo poi a chiamare il metodo and() sul primo Predicate passando come argomento il secondo. Il metodo restituisce un nuovo Predicate che combina le due condizioni.
		 * A questo punto, per avere il risultato complessivo della valutazione delle due condizioni, chiamiamo il metodo test() sul Predicate risultato di and().
		 */
		Predicate<Integer> p2 = (val) -> val % 2 == 0;
		System.out.println("Il numero 2 è maggiore di 10 ed è pari? "+ p1.and(p2).test(2));
		System.out.println("Il numero 35 è maggiore di 10 ed è pari? "+ p1.and(p2).test(35));
		System.out.println("Il numero 18 è maggiore di 10 ed è pari? "+ p1.and(p2).test(18));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Cerchiamo di valutare ora se un numero intero dato sia maggiore di 10 oppure pari. Basta riciclare entrambi i precedenti Predicate mettendoli questa volta in OR e non in AND,
		 * utilizzando quindi il metodo or() di Predicate.
		 */
		System.out.println("Il numero 3 è maggiore di 10 oppure è pari? "+ p1.or(p2).test(3));
		System.out.println("Il numero 36 è maggiore di 10 oppure è pari? "+ p1.or(p2).test(36));
		System.out.println("Il numero 8 è maggiore di 10 oppure è pari? "+ p1.or(p2).test(8));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Supponiamo invece di voler verificare se un numero intero sia maggiore di 10 e dispari. Abbiamo a disposizione un Predicate che valuti la prima condizione ma non uno che valuti
		 * la seconda (in quanto ne abbiamo uno che valuta se un numero sia pari). Potremmo creare un nuovo Predicate con una condizione specifica per i numeri dispari oppure possiamo
		 * sfruttare il predicate esistente per i numeri pari e negarne il risultato.
		 * Ecco che quindi si può usare il metodo negate() sul Predicate dei numeri pari per valutare se il numero sia dispari e poi mettere in AND questo nuovo Predicate con quello che valuta
		 * che il numero sia maggiore di 10.
		 */
		System.out.println("Il numero 3 è maggiore di 10 ed è dispari? "+ p1.and(p2.negate()).test(3));
		System.out.println("Il numero 37 è maggiore di 10 ed è dispari? "+ p1.and(p2.negate()).test(37));
		System.out.println("Il numero 18 è maggiore di 10 ed è dispari? "+ p1.and(p2.negate()).test(18));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Supponiamo di voler trovare all'interno di una lista di istruttori solo quelli che insegnano online e di volerli stampare.
		 * Possiamo definire un Predicate su un oggetto Instructor per cui, per mezzo di una lambda expression, verifichiamo se quell'istruttore presenta il flag isOnlineCourses settato a true.
		 * Una volta definito il nostro Predicate con la condizione da valutare, cicliamo sulla lista di istruttori con il metodo forEach() e definiamo una lambda che, per ogni elemento della
		 * lista, verifica tramite il metodo test() del Predicate se la condizione specificata si verifica per quell'istruttore. In caso affermativo si stampano le informazioni dell'istruttore
		 * altrimenti non si fa nulla.
		 */
		List<Instructor> instructorList = Instructors.getAll();
		Predicate<Instructor> p3 = instructor -> instructor.isOnlineCourses();
		instructorList.forEach(instructor -> {
			if(p3.test(instructor)) {
				System.out.println(instructor);
			}
		});
		System.out.println("------------------------------------------------------------");
		
		/**
		 * In questo esempio leggermete più complesso si vuole valutare quali istruttori insegnino online e abbiano più di 10 anni di esperienza per poi stamparli a video.
		 * La prima condizione l'abbiamo a disposizione dal Predicate usato nell'esempio precedente perciò dobbiamo definire solo un secondo Predicate per valutare la condizione sugli anni
		 * di esperienza dell'istruttore.
		 * A questo punto, si cicla come al solito sulla lista con il metodo forEach(), si deinisce un Consumer per mezzo di una lambda che valuta entrambe le condizioni necessarie mettendole
		 * in AND e, in caso il risultato sia true, stampi a video lo stato dell'istruttore.
		 */
		Predicate<Instructor> p4 = instructor -> instructor.getYearsOfExperience() > 10;
		instructorList.forEach(instructor -> {
			if(p3.and(p4).test(instructor)) {
				System.out.println(instructor);
			}
		});
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Versione alternativa del precedente esempio in cui si usano le Stream API di Java. Innanzitutto, definiamo un Consumer su un oggetto Instructor per definire le azioni che andranno
		 * compiute per gli istruttori che rispettano le condizioni che abbiamo definito per pezzo dei Predicate (insegnano online e hanno più di 10 anni di esperienza).
		 * A questo punto, si cicla sulla lista aprendo degli stream paralleli (metodo parallelStream() sulla lista), si applica un filtro sfruttando i due Predicate a disposizione in AND e
		 * si cicla sulla sottolista prodotta dal filtro con il metodo forEach() passandogli come argomento il Consumer precedentemente definito contenente le azioni da eseguire su ogni
		 * istruttore che rispetta le condizioni che ci siamo prefissati.
		 */
		Consumer<Instructor> consumer = instructor -> System.out.println(instructor);
		instructorList.parallelStream()
			.filter(p3.and(p4))
			.forEach(consumer);
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static void specializzazioniPredicate()
	{
		System.out.println(">>> SPECIALIZZAZIONI PREDICATE <<<\n");
		
		/**
		 * Come per i Consumer, anche per i Predicate esistono numerose specializzazioni che permettono di associare implicitamente al Predicate un tipo specifico di oggetto, senza specificarlo
		 * esplicitamente come tipo generico.
		 * In questo esempio vediamo l'utilizzo dell'IntPredicate (Predicate su un numero intero) per valutare se un dato numero sia maggiore di 100.
		 */
		IntPredicate p1 = val -> val > 100;
		System.out.println("IntPredicate - Valore: 100 - Risultato: "+ p1.test(100));
		System.out.println("IntPredicate - Valore: 275 - Risultato: "+ p1.test(275));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Analogamente, valutiamo la medesima condizione su un numero long per mezzo di un LongPredicate.
		 */
		LongPredicate p2 = val -> val > 100L;
		System.out.println("LongPredicate - Valore: 100 - Risultato: "+ p2.test(100));
		System.out.println("LongPredicate - Valore: 275 - Risultato: "+ p2.test(275));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Utilizziamo due DoublePredicate per valutare due condizioni su un numero decimale double. Ci interessa sapere se un numero sia minore di 100.25 e maggiore di 100.1.
		 * Per farlo, come già visto precedentemente, basta definire due Predicate con le due specifiche condizioni da rispettare per poi metterli in AND logico per mezzo del
		 * metodo and() dell'interfaccia funzionale Predicate.
		 */
		DoublePredicate p3 = val -> val < 100.25;
		DoublePredicate p4 = val -> val > 100.1;
		System.out.println("DoublePredicate - Valore: 100.15 - Risultato: "+ p3.and(p4).test(100.15));
		System.out.println("DoublePredicate - Valore: 90 - Risultato: "+ p3.and(p4).test(90));
		System.out.println("------------------------------------------------------------\n");
	}
}