package modelo;

import agenda.AgendaMedicoAVL;

/**
 * Clase que representa a un médico que tiene una agenda de turnos.
 */
public class Medico {
    public String matricula;
    public String nombre;
    public String especialidad;
    public AgendaMedicoAVL agenda;

    public Medico(String matricula, String nombre, String especialidad){
        this.matricula = matricula;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.agenda = new AgendaMedicoAVL(this);
    }

    @Override
    public String toString(){
        return matricula + " - " + nombre + " (" + especialidad + ")";
    }
}
