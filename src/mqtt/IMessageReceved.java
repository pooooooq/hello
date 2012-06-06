package mqtt;

public interface IMessageReceved {
	public void processMessage(String topic,String message);
}
