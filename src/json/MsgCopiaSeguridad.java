package json;
import java.sql.Date;

/**
 *
 * @author Aitor Piñeiro
 */
public class MsgCopiaSeguridad {
    Date date;
    String message, evento, emisor;

    public MsgCopiaSeguridad() {
    }
    public MsgCopiaSeguridad(String chat, Date date, String username, String message) {
        this.date =date;
        this.evento = chat;
        this.emisor=username;
        this.message = message;
    }

    public String getChat() {
        return evento;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return emisor;
    }

    public void setChat(String chat) {
        this.evento = chat;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.emisor = username; 
    }

    @Override
    public String toString() {
        return (evento +  ";" + date +";" + emisor + ";" + message); 
    }
    
    
}
