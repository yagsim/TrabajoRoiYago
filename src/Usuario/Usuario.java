/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import java.time.LocalDate;

/**
 *
 * @author yagos
 */
public class Usuario implements Comparable{
     private String nombre;
    private String dni;
    private int cPost;
    private String fechaN;
    private String correo;
    private int tlf;

 
    public Usuario(String nombre, String dni, int cPost, String fechaN, String correo, int tlf) {
        this.nombre = nombre;
        this.dni = dni;
        this.cPost = cPost;
        this.fechaN = fechaN;
        this.correo = correo;
        this.tlf = tlf;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public int getcPost() {
        return cPost;
    }
    public void setcPost(int cPost) {
        this.cPost = cPost;
    }

    public String getFechaN() {
        return fechaN;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTlf() {
        return tlf;
    }
    public void setTlf(int tlf) {
        this.tlf = tlf;
    }

    public int obtenerEdad(){
        int edad = 0;
        String fechaA = String.valueOf(LocalDate.now());
        String[] fechaNac = fechaN.split("\\/");
        String[] fechaAct = fechaA.split("\\-");

        if (Integer.parseInt(fechaAct[0])-Integer.parseInt(fechaNac[2]) < 18) {
            edad = Integer.parseInt(fechaAct[0])-Integer.parseInt(fechaNac[2]);
        } else if (Integer.parseInt(fechaAct[0])-Integer.parseInt(fechaNac[2]) > 18) {
            edad = Integer.parseInt(fechaAct[0])-Integer.parseInt(fechaNac[2]);
        } else {
            if (Integer.parseInt(fechaAct[1]) > Integer.parseInt(fechaNac[1])) {
                edad = 18;
            } else if (Integer.parseInt(fechaAct[1]) < Integer.parseInt(fechaNac[1])) {
                edad = 17;
            } else {
                if (Integer.parseInt(fechaAct[2]) > Integer.parseInt(fechaNac[0])) {
                    edad = 18;
                } else {
                    edad = 17;
                }
            }
        }
        return edad;
    }

    public int compareToByID(Object o) {
        Usuario usr = (Usuario) o;
        if (dni.compareToIgnoreCase(usr.dni) > 0) {
            return 1;
        } else if (dni.compareToIgnoreCase(usr.dni) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
    public int compareToByDate(Object o) {
        Usuario usr = (Usuario) o;
        String[] thisFecha = this.fechaN.split("\\/");
        String[] compareFecha = usr.fechaN.split("\\/");
        if (Integer.parseInt(thisFecha[2]) > Integer.parseInt(compareFecha[2])) {
            return 1;
        } else if (Integer.parseInt(thisFecha[2]) < Integer.parseInt(compareFecha[2])) {
            return -1;
        } else {
            if (Integer.parseInt(thisFecha[1]) > Integer.parseInt(compareFecha[1])) {
                return 1;
            } else if (Integer.parseInt(thisFecha[1]) < Integer.parseInt(compareFecha[1])) {
                return -1;
            } else {
                if (Integer.parseInt(thisFecha[0]) > Integer.parseInt(compareFecha[0])) {
                    return 1;
                } else if (Integer.parseInt(thisFecha[0]) < Integer.parseInt(compareFecha[0])) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        Usuario us1=(Usuario) o;
        if(this.nombre.compareToIgnoreCase(us1.nombre)>0)
            return 1;
        else if(this.nombre.compareToIgnoreCase(us1.nombre)<0)
            return -1;
        else
            return 0;
    }
}
