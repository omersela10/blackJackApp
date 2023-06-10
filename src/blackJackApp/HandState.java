package blackJackApp;

public interface HandState {

	void split();
	void surrender();
	void stand();
	void hit();
	void doubleDown();
}
