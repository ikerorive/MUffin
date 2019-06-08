package json;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;

/**
 *
 * @author Aitor Piñeiro
 */
public class MensajesCopia {
    List<MsgCopiaSeguridad> msgCopiaSeguridad;

    public MensajesCopia(){
        
    }
    
    public MensajesCopia(List<MsgCopiaSeguridad> msgCopiaSeguridad) {
        this.msgCopiaSeguridad=msgCopiaSeguridad;
    }
    

    public List<MsgCopiaSeguridad> getMsgCopiaSeguridad() {
        return msgCopiaSeguridad;
    }

    public void setMsgCopiaSeguridad(List<MsgCopiaSeguridad> msgCopiaSeguridad) {
        this.msgCopiaSeguridad = msgCopiaSeguridad;
    }
    
}
