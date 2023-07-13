package it.udemy;

import it.udemy.consumer.Instructor;
import it.udemy.consumer.Instructors;
import it.udemy.lambda.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

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
		predicateBiConsumer();
		biPredicate();
		function();
		biFunction();
		specializzazioniFunction();
		supplier();
		methodReference();
		streams();
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
	
	private static void predicateBiConsumer()
	{
		System.out.println(">>> PREDICATE CON BICONSUMER <<<\n");
		
		/**
		 * In questo esempio proveremo ad usare una combinazione di Predicate e BiConsumer per stampare la lista degli istruttorii che hanno più di 10 anni di esperienza e che insegnano online.
		 * Innanzitutto, salviamo la lista degli istruttori dopo aver chiamato il metodo statico Instructors.getAll().
		 * A questo punto possiamo definire due Predicate distinti che rappresenteranno le due condizioni che l'istruttore deve rispettare.
		 * Dopodiché definiamo un BiConsumer che contenga le azioni che devono essere svolte sugli istruttori che rispettano le condizioni date, ovvero stampare il nome e la lista dei corsi
		 * (per questo motivo il BiConsumer ha come generics una String per il nome e una lista di String per i corsi).
		 * Non resta che ciclare sulla lista con il metodo forEach() per poi cercare tramite un if quali istruttori della lista rispettano le condizioni che abbiamo definito (rappresentate
		 * dai due Predicate messi in AND). Quando ne viene trovato uno che rispetti le condizioni, si chiama il metodo accept() sul BiConsumer passandogli in input il nome e la lista dei corsi
		 * dell'istruttore in esame, in modo che vengano stampati.
		 */
		List<Instructor> instructors = Instructors.getAll();
		Predicate<Instructor> instOnlinePred = instructor -> instructor.isOnlineCourses();
		Predicate<Instructor> instOlderThan10Pred = instructor -> instructor.getYearsOfExperience() > 10;
		BiConsumer<String, List<String>> biconsumer = (nome, corsi) -> System.out.println("Nome: "+ nome +" - Corsi: "+ corsi);
		
		instructors.forEach(instructor -> {
			if(instOnlinePred.and(instOlderThan10Pred).test(instructor))
				biconsumer.accept(instructor.getName(), instructor.getCourses());
		});
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static void biPredicate()
	{
		System.out.println(">>> BIPREDICATE <<<\n");
		
		/**
		 * Questa è una versione alternativa e più sintetica dell'esercizio effettuato con i Predicate e BiConsumer che fa uso di un BiPredicate, ovvero una specializzaizione
		 * del Predicate che accetta due oggetti generici in input.
		 * Seguendo lo schema dell'esercizio precedente, andiamo a salvare in una variabile la lista degli istruttori in una variabile dopodiché, anziché definire due Predicate separati
		 * che rappresentino le due condizioni che ci interessano (istruttore che insegna online e che abbia più di 10 anni di esperienza), definiamo un BiPredicate unico.
		 * Il BiPredicate viene definito in modo da accettare un Boolean (valore di isOnlineCourses()) e un Integer (gli anni di esperienza dell'istruttore). Come sempre, il BiPredicate
		 * viene definito sulla base di una lambda expression che restituisce true se il parametro booleano online è true e se il parametro intero exp è maggiore di 10, false altrimenti.
		 * In questo modo, a differenza del caso precedente, abbiamo la possibilità di valutare in un colpo solo entrambe le condizioni che ci interessano all'interno di un unico Predicate
		 * (in questo caso specifico, BiPredicate).
		 * Infine, si cicla come sempre sulla lista di istruttori, si verifica con una lambda e un if se l'istruttore che stiamo esaminando rispetta le condizioni definitie dal BiPredicate
		 * (per mezzo del metodo test()) e, come prima, si chiama il metodo accept() sul BiConsumer per stamaparne il nome e la lista dei corsi nel caso il BiPredicate restituisca true.
		 */
		List<Instructor> instructors = Instructors.getAll();
		BiPredicate<Boolean, Integer> bp = (online, exp) -> online && (exp > 10);
		BiConsumer<String, List<String>> biconsumer = (nome, corsi) -> System.out.println("Nome: "+ nome +" - Corsi: "+ corsi);
		
		instructors.forEach(instructor -> {
			if(bp.test(instructor.isOnlineCourses(), instructor.getYearsOfExperience()))
				biconsumer.accept(instructor.getName(), instructor.getCourses());
		});
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static void function()
	{
		System.out.println(">>> FUNCTION <<<\n");
		
		/**
		 * Function è un'interfaccia funzionale di Java che accetta come parametri due oggetti: uno rappresenta l'input e l'altro l'output da restituire.
		 * Supponiamo di voler definire una Function che, dato un numero intero, ne restituisca la radice quadrata. Dobbiamo quindi definireuna Function che accetti come parametri
		 * un Integer (numero in input alla funzione) e un Double (il risultato della radice quadrata dell'input da restituire in output).
		 * Il comportamento della Function verrà quindi definito per mezzo di una lambda tale per cui, dato un numero in input, questa restituisce la sua radice quadrata per mezzo
		 * del metodo Math.sqrt() di Java.
		 * Quando si vuole ottenere il risultato della Function, occorre chiamare il metodo apply().
		 */
		Function<Integer, Double> sqrt = number -> Math.sqrt(number);
		System.out.println("Numero: 64 - Radice quadrata: "+ sqrt.apply(64));
		System.out.println("Numero: 81 - Radice quadrata: "+ sqrt.apply(81));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Caso analogo per una Function basata sulle stringhe: si vuole definire una Function che, data in input una stringa, ne restituisca in output la versione in sole lettere minuscole.
		 * Si crea quindi una Function che accetti due oggetti String e la si inizializza per mezzo di un'espressione lambda tale per cui, data una stringa in input, ne viene restituita la
		 * versione in minuscolo per mezzo del metodo toLowerCase() della classe String.
		 */
		Function<String, String> lower = s -> s.toLowerCase();
		System.out.println("Stringa: CIAO - Risultato: "+ lower.apply("CIAO"));
		System.out.println("Stringa: Prova - Risultato: "+ lower.apply("Prova"));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * In questo esempio, definiamo una Function che concateni la stringa SUFFISSO ad una stringa data in input. La Function sarà quindi basata su due oggetti String e il suo comportamento
		 * verrà definito per mezzo di una lambda tale per cui, data una stringa in input, ne viene restituita una in output data dalla concatenazione di questa con SUFFISSO.
		 * Vediamo poi come utilizzare il metodo andThen() di Function per concatenare due Function distinte.
		 * Data una striga, vogliamo prima trasformarla in minuscolo e poi concatenarci SUFFISSO. Usiamo quindi la prima Function lower e, per mezzo del metodo andThen(), concateniamo la seconda
		 * Function concat, dopodiché ne calcoliamo il risultato complessivo per mezzo del metodo apply() e lo stampiamo.
		 */
		Function<String, String> concat = (s1) -> s1.concat("SUFFISSO");
		System.out.println(lower.andThen(concat).apply("Prefisso"));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Definiamo una Function che prenda in input una lista di istruttori e restituisca in output una mappa con chiave il nome dell'istruttore e come valore i suoi anni di esperienza.
		 * La Function quindi viene definita sulla base di una lambda che richiede in input una lista di istruttori, inizializza un'HashMap vuota, cicla sulla lista degli istruttori e
		 * inserisce nella mappa il nome di ogni istruttore associato ai relativi anni di esperienza e, infine, restituisce tale mappa.
		 * La mappa viene poi stampata chiamando il metodo apply della Function a cui viene passata come argomento la lista di istruttori.
		 */
		List<Instructor> instructors = Instructors.getAll();
		Function<List<Instructor>, Map<String, Integer>> instMapFunction = list -> {
			HashMap<String, Integer> map = new HashMap<>();
			list.forEach(elem -> map.put(elem.getName(), elem.getYearsOfExperience()));
			return map;
		};
		System.out.println(instMapFunction.apply(instructors));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Volendo complicarci un po' le cose, creiamo una Function che produca una mappa composta dal nome degli istruttori e i loro anni di esperienza ma solo degli istruttori che tengono
		 * lezioni online.
		 * Definiamo innanzitutto un Predicate per definire la condizione che gli istruttori devono rispettare, ovvero che tengano dei corsi online: si ha quindi un Predicate basato su
		 * un oggetto Instructor inizializzato su una lambda che, dato l'istruttore, verifichi il valore del flag onlineCourses.
		 * La condizione è definita, passiamo quindi alla funzione da svolgere. Definiamo una Function simile alla precedente che prenda in input una lista di istruttori e restituisca in
		 * output una mappa <String, Integer> in cui la chiave sarà il nome dell'istruttore e il valore sarà il numero di anni di esperienza.
		 * Il comportamento di questa Function viene definito da una lambda che, data una lista di istruttori, inizializza una mappa vuota, cicla sulla lista di istruttori e, per ogni suo
		 * elemento, verifica se questo rispetta la condizione data dal Predicate che abbiamo creato (onlineCourses = true), per mezzo del metodo test() del Predicate.
		 * Se sì, aggiunge alla mappa, altrimenti non fa nulla. In ogni caso, restituisce poi la mappa prodotta.
		 * Infine, come nell'esempio precedente, si stampa il risultato della Function chiamando su di essa il metodo apply() e passandole come argomento la lista degli istruttori da processare.
		 */
		Predicate<Instructor> p = instructor -> instructor.isOnlineCourses();
		Function<List<Instructor>, Map<String, Integer>> instMapFunctionPred = list -> {
			HashMap<String, Integer> map = new HashMap<>();
			list.forEach(elem -> {
				if(p.test(elem))
					map.put(elem.getName(), elem.getYearsOfExperience());
			});
			return map;
		};
		System.out.println(instMapFunctionPred.apply(instructors));
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static void biFunction()
	{
		System.out.println(">>> BIFUNCTION <<<\n");
		
		/**
		 * Una BiFunction è una specializzazione dell'interfaccia funzionale Function che accetta in input due oggetti e produce un output.
		 * Supponiamo di voler definire una BiFunction che, ricevuti in input una lista di istruttori e una condizione espressa da un Predicate, produca in output la lista di istruttori
		 * che insegnano online.
		 * Gli elementi che ci servono per questo esercizio sono i seguenti:
		 * 		1) la lista degli istruttori
		 * 		2) un Predicate basato su un Instructor per verificare che un dato istruttore abbia il flag onlineCourses = true
		 * 		3) una BiFunction che dati i due elementi precedenti produca il risultato desiderato
		 * 		4) un Consumer da poter utilizzare su una lista di istruttori per stamparne il contenuto in modo leggibile
		 * La lista degli istruttori viene recuperata come sempre.
		 * Il Predicate verifica banalmente che il metodo isOnlineCourses() chiamato sull'istruttore restituisca true.
		 * La BiFunction prende in input la lista di istruttori e il Predicate e produce in output una nuova lista di istruttori. Il suo comportamento è dato da una lambda che inizializza
		 * una lista di istruttori vuota, cicla sulla lista ricevuta come argomento e, su ognuno dei suoi elementi, usa il Predicate per verificare la condizione che ci interessa, per mezzo
		 * del solito metodo test(). Se la condizione è vera, ovvero se l'instruttore insegna online, lo aggiungerà alla lista risultato della Function altrimenti passa oltre.
		 * Infine viene restituita la lista con tutti gli istruttori che insegnano online.
		 * Manca solo il Consumer, basato su un oggetto Instructor, definito sulla base di una lambda che descrive le azioni da svolgere su ogni singolo istruttore della lista prodotta dalla
		 * BiFunction, ovvero una semplice stampa dell'istruttore.
		 * A questo punto, per ottenere il risultato della BiFunction appena definita, chiamiamo su di essa il metodo apply() passandogli gli argomenti necessari (lista e Predicate) e questo
		 * produrrà una lista di istruttori come risultato.
		 * Su questa lista, cicleremo con il forEach() e ognuno degli elementi verrà passato in input al metodo accept() del Consumer per essere stampato a video.
		 */
		List<Instructor> instructors = Instructors.getAll();
		Predicate<Instructor> p1 = instructor -> instructor.isOnlineCourses();
		BiFunction<List<Instructor>, Predicate<Instructor>, List<Instructor>> bf1 = (list, pred) -> {
			List<Instructor> result = new ArrayList<>();
			list.forEach(elem -> {
				if(pred.test(elem))
					result.add(elem);
			});
			return result;
		};
		Consumer<Instructor> c1 = elem -> System.out.println(elem);
		bf1.apply(instructors, p1).forEach(elem -> c1.accept(elem));
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static void specializzazioniFunction()
	{
		System.out.println(">>> SPECIALIZZAZIONI FUNCTION <<<\n");
		
		/**
		 * UnaryOperator è un'interfaccia funzionale che estende Function e che ha la particolarità di accettare come parametro lo stesso tipo di oggetto che deve restituire come output.
		 * Supponiamo di voler definire uno UnaryOperator che, dato un intero, lo restituisca moltiplicato per 100.
		 * Basta dichiarare uno UnaryOperator di Integer basato su una lambda che accetta un argomento intero e lo restituisce moltiplicato per 100.
		 * Per sfruttare lo UnaryOperator basta poi chiamare il suo metodo apply() passandogli come argomento l'intero da moltiplicare.
		 */
		UnaryOperator<Integer> unary = i -> i * 100;
		System.out.println("Numero: 5 - Risultato: "+ unary.apply(5));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Come UnaryOperator costituisce una specializzazione di Function, esistono varie specializzazioni di UnaryOperator che permettono di gestire implicitamente un certo tipo di oggetto:
		 * IntUnaryOprator (interi), LongUnaryOperator (long), DoubleUnaryOperator (numeri decimali double), analogamente a quanto visto per le specializzazioni dei Consumer.
		 * Tutte queste tipologie funzionano esenzialmente allo stesso modo: si dichiara lo UnaryOperator specifico che si desidera usare con la sua lambda che ne definisce il comporamento e,
		 * quando si intende usare l'oggetto, si chiama una specializzazione del metodo apply() specifica a seconda dei casi (applyAsInt(), applyAsLong(), applyAsDouble()...) passandogli come
		 * argomento il numero che verrà usato dallo UnaryOperator, che deve chiaramente essere compatibile con il tipo di oggetto accettato dallo stesso.
		 */
		IntUnaryOperator unaryInt = i -> i * 100;
		System.out.println("Numero: 6 - Risultato: "+ unaryInt.applyAsInt(6));
		System.out.println("------------------------------------------------------------");
		
		LongUnaryOperator unaryLong = i -> i * 100;
		System.out.println("Numero: 3 - Risultato: "+ unaryLong.applyAsLong(3));
		System.out.println("------------------------------------------------------------");
		
		DoubleUnaryOperator unaryDouble = i -> i * 100;
		System.out.println("Numero: 4.5 - Risultato: "+ unaryDouble.applyAsDouble(4.5));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Similmente agli UnaryOperator, i BinaryOperator sono delle interfacce funzionali che acettano due argomenti e restituiscono un risultato dello stesso tipo.
		 * Il funzionamento è del tutto analogo a quello degli UnaryOperator.
		 */
		BinaryOperator<Integer> binary = (x, y) -> x + y;
		System.out.println("Parametri: 5 e 6 - Risultato: "+ binary.apply(5, 6));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Come per gli Unary Operator, esistono delle specializzazioni dei BinaryOperator dedicate a determinati tipi di oggetti (Integer, Long, Double...).
		 * In modo del tutto analogo a queste specializzazioni, per gli IntBinaryOperator, LongBinaryOperator e DoubleBinaryOperator esistono delle specializzazioni del
		 * metodo apply() che si possono usare per ottenere il risultato dell'operatore.
		 */
		IntBinaryOperator intBin = (x, y) -> x + y;
		System.out.println("Parametri: 1 e 2 - Risultato: "+ intBin.applyAsInt(1, 2));
		System.out.println("------------------------------------------------------------");
		
		LongBinaryOperator longBin = (x, y) -> x + y;
		System.out.println("Parametri: 10 e 4 - Risultato: "+ longBin.applyAsLong(10, 4));
		System.out.println("------------------------------------------------------------");
		
		DoubleBinaryOperator doubleBin = (x, y) -> x + y;
		System.out.println("Parametri: 5.1 e 6.3 - Risultato: "+ doubleBin.applyAsDouble(5.1, 6.3));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * I BinaryOperator (e le relative specializzazioni) mettono anche a disposizione due metodi statici minBy() e maxBy() che restituiscono, rispettivamente, il minimo e
		 * il massimo tra i due valori ricevuti come argomento.
		 * Per determinare un'ordine di paragone tra i due oggetti (non necessariamente si parla di numeri), occorre definire un Comparator da passare in input al metodo.
		 * Una volta definito il Comparator contenente il criterio di confronto per i due oggetti dati in input al BinaryOperator, definiamo appunto il nostro BinaryOperator
		 * chiamando questa volta il metodo statico BinaryOperator.maxBy() e poi, come sempre, stampiamo il risultato del nostro operator chiamando il metodo apply().
		 */
		Comparator<Integer> comp = (x, y) -> x.compareTo(y);
		BinaryOperator binMax = BinaryOperator.maxBy(comp);
		System.out.println("Parametri: 10 e 37 - Massimo: "+ binMax.apply(10, 37));
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static void supplier()
	{
		System.out.println(">>> SUPPLIER <<<\n");
		
		/**
		 * Supplier è un'interfaccia funzionale che permette di astrarre un'operazione che non richiede alcun input ma restituisce un risultato di qualche tipo (il generic del Supplier).
		 * Il risultato del Supplier viene reso disponibile dal metodo get().
		 * Supponiamo di voler definire un Supplier che restituisca un numero casuale intero. Dichiariamo quindi una variabile Supplier<Integer> basata su una lambda che restituisce
		 * il numero casuale, calcolato mediante il metodo statico Math.rand().
		 */
		Supplier<Integer> s = () -> (int) (Math.random() * 1000);
		System.out.println("Valore casuale: "+ s.get());
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static void methodReference()
	{
		System.out.println(">>> METHOD REFERENCE <<<\n");
		
		List<Instructor> list = Instructors.getAll();
		
		/**
		 * Con Method Reference si intende una particolare sintassi di Java che permette d fare riferimento ad un metodo di una classe e di utilizzarlo come un'implementazione di
		 * un'interfaccia funzionale compatibile. E' un modo per scrivere lambda expression più concise e per riutilizzare metodi esistendi all'interno di esse senza doverli riscrivere
		 * come lambda expressions.
		 * Per rendere chiaro come sia possibile scrivere codice con questa particolare sintassi ed evidenziare il fatto che il codice scritto in questo modo più conciso sia del tutto
		 * equivalente al codice scritto normalmente in modo più esteso, facciamo qualche esempio di confronto tra queste due modalità.
		 * Supponiamo di voler definire un Predicate che permetta di valutare la condizione secondo cui un istruttore abbia dei corsi online. O si definisce questo Predicate nel modo
		 * classico con una lambda che abbiamo già esaminato esaurientemente oppure lo si può fare con la metohod reference, ovvero indicando il nome della classe contenente il metodo
		 * che ci interessa seguito da :: e dal nome del metodo senza parentesi.
		 * In questo modo, il compilatore di Java è sufficientemente elaborato da capire a quale metodo si vuole fare riferimento e quale debba essere l'eventuale suo input. In questo
		 * caso specifico, il compilatore capirà che il metodo che ci interessa è isOnlineCourses() della classe Instructor, il quale non richiede argomenti, e lo chiamerà sull'oggetto
		 * Instructor specificato nel Predicate.
		 */
		Predicate<Instructor> p1 = instructor -> instructor.isOnlineCourses();
		Predicate<Instructor> p2 = Instructor::isOnlineCourses;
		list.forEach(i -> {
			if(p1.test(i))
				System.out.print("["+ i.getName() +" - "+ i.isOnlineCourses() +"] ");
		});
		System.out.println();
		list.forEach(i -> {
			if(p2.test(i))
				System.out.print("["+ i.getName() +" - "+ i.isOnlineCourses() +"] ");
		});
		System.out.println("\n------------------------------------------------------------");
		
		/**
		 * Vediamo ora un esempio su una Function che deve calcolare la radice quadrata di un numero intero. Anche qui definiamo due Function equivalenti che svolgono lo stesso compito
		 * ma sfruttano le due sintassi disponibili.
		 * Sulla prima non ci soffermiamo, essendo sufficientemente semplice, mentre la seconda fa uso della method reference per chiamare il metodo sqrt() della classe Math di Java.
		 * A differenza dell'esempio precedente, qui utilizziamo la method reference su un metodo statico di una classe facente parte delle librerie di Java, non su un metodo di istanza
		 * di una nostra classe, ma possiamo vedere che la sintassi funziona ugualmente.
		 */
		Function<Integer, Double> f1 = num -> Math.sqrt(num);
		Function<Integer, Double> f2 = Math::sqrt;
		System.out.println("Numero 64 - Radice quadrata: "+ f1.apply(64));
		System.out.println("Numero 64 - Radice quadrata: "+ f2.apply(64));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Ora un esempio su una function che prende in input una stringa e ne restituisce la versione in lettere minuscole.
		 * La versione della function che usa la method reference chiama il metodo toLowerCase() della classe String di Java e applica questo metodo sulla stringa passata in input
		 * alla Function stessa.
		 */
		Function<String, String> fs1 = s -> s.toLowerCase();
		Function<String, String> fs2 = String::toLowerCase;
		System.out.println("Stringa: CIAO - Minuscola: "+ fs1.apply("CIAO"));
		System.out.println("Stringa: CIAO - Minuscola: "+ fs2.apply("CIAO"));
		System.out.println("------------------------------------------------------------");
		
		/**
		 * Come accennato in precedenza, la sintassi method reference non può essere utilizzata solo sui metodi nativi delle librerie di Java ma anche su metodi di classi definite
		 * da noi stessi.
		 * Supponiamo di definire un metodo experienceGreaterThan10() che, dato un istruttore come argomento, restituisca true se questo ha più di 10 anni di esperienza, false altrimenti.
		 * Nulla ci vieta di chiamare questo metodo tramite method reference sugli oggetti Instructor della nostra lista per cercare gli istruttori che soddisfano questa condizione.
		 */
		Predicate<Instructor> p3 = Main::experienceGreaterThan10;
		Consumer<Instructor> cons = instructor -> System.out.println(instructor.getName() +" - "+ instructor.getYearsOfExperience());
		list.forEach(instructor -> {
			if(p3.test(instructor))
				cons.accept(instructor);
		});
		System.out.println("------------------------------------------------------------");
		
		/**
		 * La sintassi method reference può essere utilizzata anche per far riferimento ai costruttori nelle classi. Come in qualunque altro caso e qualunque altro metodo
		 * da referenziare in questo modo, occorre che tale metodo rispecchi il tipo di interfaccia funzionale a cui si assegna il risultato.
		 * Ad esempio, supponiamo che ci serva assegnare ad un Supplier una nuova istanza di un oggetto Instructor. Possiamo referenziare il costruttore (in questo caso
		 * quello vuoto) della classe Instructor per mezzo dell'istruzione Instructor::new e assegnare il risultato ad una variabile Supplier<Instructor>, proprio perché
		 * il tipo dell'oggetto restituito dal metodo referenziato è compatibile con quello dichiarato per l'interfaccia funzionale Supplier a cui viene assegnato.
		 */
		Supplier<Instructor> supplier = Instructor::new;
		System.out.println(supplier.get());
		System.out.println("------------------------------------------------------------\n");
	}
	
	private static boolean experienceGreaterThan10(Instructor instructor)
	{
		return instructor.getYearsOfExperience() > 10;
	}

	private static void streams()
	{
		System.out.println(">>> STREAMS <<<\n");

		/**
		 * Le Stream API di Java sono dei metodi delle librerie Java che permettono di eseguire operazioni concatenate su delle collection sfruttando il paradigma
		 * della programmazione funzionale, ovvero facendo largo uso di Predicate, Consumer, lambda expressions...
		 * Le Stream API hanno il grande vantaggio di permettere di realizzare delle operazioni complesse con una sintassi molto sintetica e concatenando i metodi
		 * tra loro, in modo da rendere evidente la sequenza di operazioni che si intende svolgere su una collection.
		 * Supponiamo per questo primo esempio di avere a disposizione una lista di istruttori e di voler generare una mappa dei soli insegnanti che insegnano
		 * online e che hanno più di 10 anni di esperienza (mappa con chiave il nome dell'insegnante e come valore la lista dei suoi corsi).
		 * Definiamo innanzitutto due Predicate per descrivere le condizioni che ogni istruttore deve rispettare e poi ricaviamo la lista degli istruttori mediante
		 * la solita chiamata al metodo statico Instructors.getAll().
		 * Fatto ciò, dichiariamo la nostra mappa, che avrà chiave String e valore una List<String>.
		 * Per popolare la nostra mappa, dovremo ciclare sulla lista degli istruttori in cerca di quelli che rispettano le condizioni che ci siamo imposti, quindi
		 * chiamiamo innanzitutto il metodo stream() sulla lista per aprire appunto uno stream sulla collection e utilizziamo il metodo filter() per generare una nuova
		 * collection contenente i soli elementi della lista di partenza che soddisfano le nostre condizioni. Queste sono state espresse per mezzo di due Predicate,
		 * che metteremo in AND logico per mezzo del metodo and() e passeremo come argomento al metodo filter() dello stream.
		 * Abbiamo quindi la nostra sottolista di instruttori ma dobbiamo trasformarla in una mappa. Per farlo, chiamiamo a cascata il metodo collect() a cui passiamo
		 * come argomento il risultato del metodo statico Collectors.toMap().
		 * Andando per ordine, il metodo Collectors.toMap() di fatto cicla sulla lista filtrata e, per ogni suo elemento, va ad inserire in una mappa il nome
		 * dell'istruttore come chiave e la lista dei suoi corsi come valore (si noti l'utilizzo della sintassi method reference).
		 * Abbiamo quindi la mappa correttamente popolata che può essere stampata a video.
		 */
		Predicate<Instructor> p1 = instructor -> instructor.isOnlineCourses();
		Predicate<Instructor> p2 = instructor -> instructor.getYearsOfExperience() > 10;
		List<Instructor> list = Instructors.getAll();
		Map<String, List<String>> map = list.stream()
				.filter(p1.and(p2))
				.collect(Collectors.toMap(Instructor::getName, Instructor::getCourses));
		System.out.println(map);
		System.out.println("------------------------------------------------------------");

		/**
		 * Il metodo map() delle Stream API è un metodo che prende in input una Function e, pertanto, il suo compito è quello di permettere di eseguire delle generiche
		 * azioni sugli elementi di uno stream.
		 * Uno scopo più preciso del metodo, all'atto pratico, è quello di permettere allo sviluppatore di cambiare il tipo di oggetti di uno stream da X a Y: partendo
		 * da uno stream di oggetti X, ad esempio, è possibile chiamare ad un certo punto il metodo map() per mappare appunto l'elemento di tipo X su un elemento di
		 * tipo Y e proseguire con le successive operazioni.
		 * Supponiamo di avere a disposizione una lista di istruttori e di voler generare, a partire da questa, una lista dei loro nomi in lettere maiuscole.
		 * Apriamo quindi uno stream sulla lista di istruttori e chiamiamo il metodo map() per cambiare il tipo degli oggetti dello stream da Instructor a String,
		 * andando a prendere il nome dell'istruttore per mezzo del metodo getName(). Si cambia quindi il tipo degli elementi dello stream da Instructor a String.
		 * Chiamiamo una seconda volta map() non per cambiare nuovamente il tipo degli elementi dello stream ma per eseguire un'operazione su di essi, ovvero
		 * trasformare la stringa del nome dell'istruttore in maiuscolo.
		 * Infine, si trasforma lo stream in una lista per mezzo del metodo Collectors.toList() e si stampa il risultato.
		 */
		List<String> nomi = list.stream()
				.map(Instructor::getName)
				.map(String::toUpperCase)
				.collect(Collectors.toList());
		System.out.println(nomi);
		System.out.println("------------------------------------------------------------");

		/**
		 * Il metodo flatMap() della Stream API assolve quasi la stessa funzione di map() ma va utilizzato quando devono essere trasformate delle strutture dati complesse.
		 * Supponiamo di avere una lista di istruttori a disposizione e di voler ottenere una lista dei corsi di tutti quanti gli istruttori.
		 * Definiamo quindi una lista di stringhe e iniziamo a popolarla aprendo uno stream sulla lista di istruttori, per ciclarci sopra.
		 * Dato lo stream di istruttori, su ognuno di essi si chiama map() per ottenere la lista dei corsi, quindi per cambiare il tipo di oggetti dello stream da Instructor
		 * a List<String>.
		 * A questo punto, abbiamo uno stream di liste di string (le liste dei corsi tenuti dai singoli insegnanti) che vogliamo trasformare in uno stream di String, ovvero
		 * dei soli nomi dei corsi. Dobbiamo quindi passare da una struttura dati complessa (la lista) ad un oggetto semplice (la stringa), pertanto usiamo il metodo flatMap()
		 * passandogli come argomento il tipo di oggetto che vogliamo avere in output a partire dall'elemento considerato: partiamo da uno Stream<List<String>> quindi dovremo
		 * chiamare il metodo stream() sulla List<String> per ottenere uno Stream<String>.
		 * Fatto ciò, chiamaiamo il metodo collect() sul risultato di flatMap() per convertire il nostro stream di stringhe in una lista (contenente possibili duplicati, nel caso
		 * lo stesso corso sia tenuto da più insegnanti) e stampiamo il risultato.
		 */
		List<String> corsi = list.stream()
				.map(Instructor::getCourses)
				.flatMap(List::stream)
				.collect(Collectors.toList());
		System.out.println(corsi);
		System.out.println("------------------------------------------------------------");

		/**
		 * Supponiamo di voler effettuare il conteggio dei corsi tenuti dagli istruttori (considerando per semplicità anche eventuali duplicati), quindi la semplice somma di tutti
		 * i corsi che troviamo nelle liste dei corsi dei singoli istruttori.
		 * Per contare gli elementi facenti parte di uno stream, possiamo affidarci al metodo count().
		 * Apriamo quindi uno stream sulla lista degli istruttori e ricaviamo la lista completa dei corsi come fatto in precedenza, chiamando prima il metoto map() per ottenere le
		 * liste dei corsi dei singoli istruttori e poi il metodo flatMap() per trasformare le liste in semplici stringhe.
		 * Una volta ottenuto uno stream di stringhe (i nomi dei corsi), effettuiamo il conteggio degli elementi di questo stream con count() per poi stamparlo in output.
		 */
		long contatoreCorsi = list.stream()
				.map(Instructor::getCourses)
				.flatMap(List::stream)
				.count();
		System.out.println("Numero totale dei corsi: "+ contatoreCorsi);
		System.out.println("------------------------------------------------------------");

		/**
		 * Se volessimo effettuare un ulteriore raffinamento dell'esempio precedente eliminando i duplicati, potremmo chiamare il metodo distinct() sullo stream di stringhe fornito
		 * dal metodo flatMap() per ottenere uno stream da cui sono stati eliminati gli elementi duplicati.
		 * Su questo stream possiamo poi chiamare come prima il metodo count() per determinare il numero dei sui elementi, ovvero il numero di corsi distinti tenuti da tutti gli istruttori.
		 */
		long contatoreCorsiDistinti = list.stream()
				.map(Instructor::getCourses)
				.flatMap(List::stream)
				.distinct()
				.count();
		System.out.println("Numero reale dei corsi (duplicati esclusi): "+ contatoreCorsiDistinti);
		System.out.println("------------------------------------------------------------");

		/**
		 * Possiamo anche ordinare gli elementi di uno stream secondo un ordine naturale, sfruttando il metodo sorted().
		 * Ad esempio possiamo ordinare alfabeticamente i nomi dei corsi tenuti dagli istruttori.
		 * Dichiariamo quindi una lista di stringhe (i nomi dei corsi) e apriamo uno stream sulla lista degli istruttori.
		 * Ricaviamo poi uno stream di String contenente i nomi di tutti i corsi (tramite map() e flatMap(), come visto in precedenza), eliminiamo i duplicati con distinct(),
		 * ordiniamo alfabeticamente (essendo stringhe) gli elementi di questo stream chiamando il metodo sorted() e generiamo la lista finale mediante il metodo collect(),
		 * per poi stampare il risultato.
		 */
		List<String> corsiOrdinati = list.stream()
				.map(Instructor::getCourses)
				.flatMap(List::stream)
				.distinct()
				.sorted()
				.collect(Collectors.toList());
		System.out.println("Lista dei corsi ordinati: "+ corsiOrdinati);
		System.out.println("------------------------------------------------------------");

		/**
		 * Esiste anche una versione di sorted() che permette di effettuare un ordinamento degli elementi di uno stream che sia differente da quello naturale, per mezzo di
		 * un Comparator specifico ricevuto come argomento.
		 * Supponiamo ad esempio di voler ordinare i nomi dei corsi ma in ordina alfabetico inverso.
		 * Il procedimento sarà analogo a quello dell'esempio precedente ma il metodo sorted() dovrà ricevere come argomento un Comparator specifico che descriva la modalità
		 * di ordinamento da applicare, essendo differente da quella naturale. Passiamo quindi, in questo caso, Comparator.reverseOrder().
		 * La lista risultato delle nostre operazioni sarà quindi la lista dei corsi in ordine alfabetico inverso.
		 */
		List<String> corsiInvertiti = list.stream()
				.map(Instructor::getCourses)
				.flatMap(List::stream)
				.distinct()
				.sorted(Comparator.reverseOrder())
				.collect(Collectors.toList());
		System.out.println("Lista dei corsi invertiti: "+ corsiInvertiti);
		System.out.println("------------------------------------------------------------");

		/**
		 * Un altro modo per eseguire un ordinamento degli elementi di uno stream, sempre per mezzo del metodo sosrted(). In questo caso, cerchiamo di ordinare gli oggetti
		 * Instructor per nome dell'istruttore. Il metodo sosrted() necessita di un oggetto Comparable in input, che possiamo ottenere da una chiamata al metodo statico
		 * Comparator.comparing(). Questo a sua volta necessita come argomento di una Function per determinare l'oggetto su cui effettuare l'ordinamento (nel nostro caso
		 * il nome dell'istruttore, quindi Instructor::getName).
		 */
		List<Instructor> sortedInstructors = list.stream()
				.sorted(Comparator.comparing(Instructor::getName))
				.collect(Collectors.toList());
		sortedInstructors.forEach(System.out::println);
		System.out.println("------------------------------------------------------------");

		/**
		 * Il metodo anyMatch() permette di verificare se almeno uno degli elementi di uno stream rispetta la condizione specificata dal Predicate che viene passato come argomento
		 * al metodo. Il metodo restituisce true, se almeno uno degli elementi rispetta la condizione, false altrimenti.
		 */
		boolean match = list.stream()
				.map(Instructor::getCourses)
				.flatMap(List::stream)
				.anyMatch(course -> course.startsWith("Java"));
		System.out.println("Esistono corsi che iniziano con la stringa 'Java'? "+ match);
		System.out.println("------------------------------------------------------------");

		/**
		 * Il metodo allMatch() permette di verificare se tutti gli elementi di uno stream rispettano la condizione specificata per mezzo del Predicate passato come argomento
		 * al metodo. Se tutti gli elementi rispettano la condizione, restituisce true altrimenti false.
		 */
		boolean matchCompleto = list.stream()
				.map(Instructor::getCourses)
				.flatMap(List::stream)
				.allMatch(course -> course.startsWith("Java"));
		System.out.println("Tutti i corsi iniziano con la stringa 'Java'? "+ matchCompleto);
		System.out.println("------------------------------------------------------------");

		/**
		 * Il metodo noneMatch() è il complementare di allMatch() e verifica se nessuno degli elementi dello stream rispetta la condizione indicata dal Predicate. Se nessuno
		 * la rispetta restituisce true, altrimenti false.
		 */
		boolean noneMatch = list.stream()
				.map(Instructor::getCourses)
				.flatMap(List::stream)
				.noneMatch(course -> course.startsWith("Java"));
		System.out.println("Tutti i corsi non iniziano con la stringa 'Java'? "+ noneMatch);
		System.out.println("------------------------------------------------------------");

		/**
		 * Il metodo reduce() permette di applicare ricorsivamente una determinata operazione sugli elementi di uno stream per operare una riduzione o aggregazione di tali elementi
		 * e fornire un risultato in output.
		 * Come esempio pratico, supponiamo di voler calcolare la somma dei numeri interi da 1 a 9 inclusi.
		 * Definiamo una lista di interi contenente i numeri da sommare dopodiché apriamo su questa lista uno stream e chiamiamo il metodo reduce().
		 * Questo necessita di due argomenti, un oggetto di partenza dello stesso tipo degli elementi dello stream (nel nostro caso un intero) e un BinaryOperator di elementi sempre
		 * dello stesso tipo: in altre parole abbiamo il valore di partenza e l'azione che il metodo deve eseguire per ogni elemento dello stream.
		 * Il valore di partenza sarà 0 perché il calcolo della somma dovrà partire da zero (elemento neutro) mentre il BinarOperator sarà dato da una lambda per cui, dati due elementi
		 * (interi), questa calcola la somma dei due e la restituisce.
		 * Il metodo reduce() cicla sullo stream di elementi interi e li somma uno alla volta al risultato dell'operazione svolta sul precedente elemento (a parte il primo elemento
		 * che verrà sommato al valore di partenza 0, passato come argomento).
		 * Si avrà quindi che il primo elmento 1 verrà sommato a 0 dando come risultato 1, il secondo elemento 2 verrà sommato alla somma precedente 1 dando 3 e così via fino ad arrivare
		 * ad avere il risultato finale dato dalla somma dell'ultimo elemento dello stream con il risultato dell'operazione svolta su tutti i precedenti elementi dello stream.
		 */
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		int sum = numbers.stream()
				.reduce(0, (a, b) -> a+b);
		System.out.println("Somma: "+ sum);
		System.out.println("------------------------------------------------------------");

		/**
		 * Caso analogo per il calcolo del prodotto dei primi 9 numeri interi. Il procedimento è analogo a quello della somma, si usa sempre il metodo reduce() degli stream, ma si fissa
		 * il valore di partenza 1, non più zero, perché nel caso del prodotto è 1 ad essere l'elemento neutro. Se indicassimo 0 come valore di partenza, questo andrebbe ad annullare
		 * il prodotto per ogni numero dello stream.
		 */
		int product = numbers.stream()
				.reduce(1, (a, b) -> a*b);
		System.out.println("Prodotto: "+ product);
		System.out.println("------------------------------------------------------------");

		/**
		 * Esiste una variante di reduce() che non necessita del valore di partenza, che prende come argomento il solo BinaryOperator e restituisce in output un Optional.
		 * Questo permette di gestire la presenza o meno di un risultato in quanto se la sequenza di operazioni effettuate da reduce() va a buon fine, viene restituito un Optional
		 * contenente il risultato complessivo, altrimenti viene dato un Optional vuoto, ad indicare la mancanza del risultato.
		 */
		Optional<Integer> sum2 = numbers.stream()
				.reduce((a, b) -> a+b);
		System.out.println("Somma (optional valorizzato): "+ sum2.get());
		System.out.println("------------------------------------------------------------");

		/**
		 * In caso il metodo reduce() non fornisca alcun risultato, questo corrisponderà ad un Optional vuoto. Per ottenere il valore contenuto in un Optional occorre chiamare il metodo
		 * get() ma occorre assicurarsi prima che l'Optional non sia vuoto altrimenti viene lanciata un'eccezione.
		 * Prima di stampare il risultato del metodo reduce() occorre quindi verificare se sia presente un metodo nell'Optional sfruttando il metodo ifPresent() o, come in questo caso,
		 * ifPresentOrElse(). Volendo percorrere questa seconda strada, come primo argomento al metodo ifPresentOrElse() dobbiamo passare un Consumer che specifica l'azione da svolgere
		 * sull'oggetto contenuto nell'Optional (in questo caso una semplice stampa) mentre il secondo argomento è una Runnable che indica l'azione da svolgere in caso contrario (in
		 * questo caso una lambda che, senza ricevere nulla in input, stampa la stringa "vuoto").
		 */
		List<Integer> emptyList = new ArrayList<>();
		Optional<Integer> sum3 = emptyList.stream()
				.reduce((a, b) -> a+b);
		System.out.print("Somma (optional vuoto): ");
		sum3.ifPresentOrElse(System.out::println, () -> System.out.println("vuoto"));
		System.out.println("------------------------------------------------------------");

		Optional<Instructor> instructor = list.stream()
				.reduce((inst1, inst2) -> {
					if(inst1.getYearsOfExperience() > inst2.getYearsOfExperience())
						return inst1;
					else
						return inst2;
				});
		System.out.print("Istruttore col maggior numero di anni di esperienza: ");
		instructor.ifPresentOrElse(System.out::println, () -> System.out.println("vuoto"));
		System.out.println("------------------------------------------------------------");
	}
}