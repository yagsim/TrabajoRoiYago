/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Escritura;

import Lectura.Lectura;
import Usuario.Usuario;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import libreriaroiyago.Llamar;

/**
 *
 * @author yagos
 */
public class Escritura {
     private static void escribirArrayListEnFichero(ArrayList<Usuario> usuarios, File fich) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fich));
        for (int i = 0; i < usuarios.size(); i++) {
            bw.write(usuarios.get(i).getNombre()+","+usuarios.get(i).getDni()+","+usuarios.get(i).getcPost()+","+usuarios.get(i).getFechaN()+","+usuarios.get(i).getCorreo()+","+usuarios.get(i).getTlf()+"\n");
        }
        bw.close();
    }

    public void añadirUsuario(ArrayList<Usuario> usuarios, File fich) throws IOException {
        try {
            Lectura.vertirFicheroEnArrayList(usuarios, fich);

            String nombre = Llamar.lerString("Nombre:");
            String dni = pedirDNI(usuarios, fich);
            int cPostal = pedircPostal("Código Postal:");
            String fNac = pedirFNac();
            String correo = pedirCorreo("Correo electrónico:");
            int tlf = pedirTlf("Nº de teléfono (opcional) \nPulsar enter para omitir");

            usuarios.add(new Usuario(nombre, dni, cPostal, fNac, correo, tlf));

            escribirArrayListEnFichero(usuarios, fich);

        } catch (FileNotFoundException ex) {
            System.out.println("error1.1: "+ex.toString());
        }
    }

    public static void eliminar(ArrayList<Usuario> usuarios, File nombreFichero,String DNI) throws IOException {
        try {
            Lectura.vertirFicheroEnArrayList(usuarios, nombreFichero);

            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getDni().equals(DNI)) {
                    usuarios.remove(i);
                    i = i - 1;
                }
            }

            Escritura.escribirArrayListEnFichero(usuarios, nombreFichero);

            Llamar.amosar("Usuario eliminado");

        } catch (FileNotFoundException e) {
            System.out.println("fichero no encontrado" + e.toString());
        }
    }

    public static void modificar(ArrayList<Usuario> usuarios, File nombreFichero, String DNI) throws IOException {
        try {
            Lectura.vertirFicheroEnArrayList(usuarios, nombreFichero);
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getDni().equals(DNI)) {
                    int opcion;
                    opcion = Llamar.lerInt("MENU \n1. Correo \n2. Código postal \n3. Teléfono \n4. Atrás");
                    switch (opcion) {
                        case 1:
                            String nuevo_Correo = pedirCorreo("Introduzca el nuevo correo:");
                            usuarios.get(i).setCorreo(nuevo_Correo);
                            break;
                        case 2:
                            int nuevo_CodigoPostal = pedircPostal("Introduzca el nuevo código postal:");
                            usuarios.get(i).setcPost(nuevo_CodigoPostal);
                            break;
                        case 3:
                            int nuevo_Telefono = pedirTlf("Introduzca nuevo nº de teléfono:");
                            usuarios.get(i).setTlf(nuevo_Telefono);
                            break;
                        default:
                            break;
                    }
                    break;
                }
            }

            Escritura.escribirArrayListEnFichero(usuarios, nombreFichero);

        } catch (FileNotFoundException e) {
            System.out.println("error (fichero no encontrado)"+e.toString());
        }
    }

    private static int pedirTlf(String mensaje) {
        String tlf = null;
        while (tlf == null) {
            try {
                tlf = String.valueOf(Llamar.lerString(mensaje));
                if (tlf.length() == 9) {
                    if (tlf.startsWith("6") || tlf.startsWith("9") || tlf.startsWith("7")) {
                    } else {
                        System.out.println("Formato del nª de teléfono erróneo");
                        tlf = null;
                    }
                } else {
                    if (tlf.length() == 0) {
                        tlf = "0";
                        break;
                    } else {
                        System.out.println("Tamaño del nº de teléfono incorrecto");
                        tlf = null;
                    }
                }
            } catch (NumberFormatException ex) {
                if (tlf.equals("")) {
                    tlf = "0";
                    break;
                }else {
                    System.out.println("Formato incorrecto");
                    tlf=null;
                }
            }

        }
        return Integer.parseInt(tlf);
    }
    private static String pedirCorreo(String mensaje) {
        String correo = null;
        while (correo == null) {
            correo = Llamar.lerString(mensaje);
            if (correo.contains("@")) {
                //validar que no tiene más de 1 "@"
                int contA = 0;
                for (int i = 0; i < correo.length(); i++) {
                    if (correo.charAt(i) == '@') {
                        contA++;
                    }
                }
                if (contA == 1) {
                    // continuación de las validaciones
                    String[] lista = correo.split("@");
                    //validar que el dominio no tiene más de un punto
                    int contP = 0;
                    for (int i = 0; i < lista[1].length(); i++) {
                        if (lista[1].charAt(i) == '.') {
                            contP++;
                        }
                    }
                    if (contP == 1) {
                        //continuacion de las validaciones
                        String[] dominio = lista[1].split("\\.");
                        //validar el tipo
                        if (dominio[1].equals("es") || dominio[1].equals("com") || dominio[1].equals("org")) {
                        }else {
                            System.out.println("Tipo del dominio erróneo");
                            correo = null;
                        }
                    } else {
                        System.out.println("Formato del dominio incorrecto");
                        correo = null;
                    }
                } else {
                    System.out.println("Formato del correo incorrecto");
                    correo = null;
                }
            }else {
                System.out.println("Formato del correo incorrecto");
                correo = null;
            }
        }
        return correo;
    }
    private String pedirFNac() {
        int mes = 0;
        while (mes == 0) {
            try {
                mes = Llamar.lerInt("Fecha de nacimiento\nMes:");
                if (mes < 1 || mes > 12) {
                    System.out.println("Mes inválido");
                    mes = 0;
                }
            } catch (NumberFormatException ex) {
                System.out.println("El formato debe ser numérico");
                mes = 0;
            }
        }
        int anho = 0;
        while (anho == 0) {
            try {
                anho = Llamar.lerInt("Fecha de nacimiento\nAño:");
                if (anho < 1910 || anho > 2050) {
                    System.out.println("Año fuera del límite permitido");
                    anho = 0;
                }
            } catch (NumberFormatException ex) {
                System.out.println("El formato debe de ser numérico");
                anho = 0;
            }
        }
        int dia = 0;
        while (dia == 0) {
            try {
                dia = Llamar.lerInt("Fecha de nacimiento\nDía:");
                switch (mes) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        if (dia < 1 || dia > 31) {
                            System.out.println("Día incorrecto");
                            dia = 0;
                        }
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        if (dia < 1 || dia > 30) {
                            System.out.println("Día incorrecto");
                            dia = 0;
                        }
                        break;
                    case 2:
                        if ((anho % 4)==0 && (anho % 100)!=0) {
                            if (dia < 1 || dia > 29) {
                                System.out.println("Día incorrecto");
                                dia = 0;
                            }
                        } else {
                            if ((anho % 100)==0 && (anho % 400)==0) {
                                if (dia < 1 || dia > 29) {
                                    System.out.println("Día incorrecto");
                                    dia = 0;
                                }
                            } else {
                                if (dia < 1 || dia > 28) {
                                    System.out.println("Día incorrecto");
                                    dia = 0;
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            } catch (NumberFormatException ex) {
                System.out.println("El formato debe de ser numérico");
            }
        }

        String fecha = dia+"/"+mes+"/"+anho;
        return fecha;
    }
    private static int pedircPostal(String mensaje) {
        int cPostal = 0;
        while (cPostal == 0) {
            try {
                cPostal = Llamar.lerInt(mensaje);
                if (String.valueOf(cPostal).length() != 5) {
                    System.out.println("Tamaño del c.postal incorrecto");
                    cPostal = 0;
                }
            } catch (NumberFormatException nfex) {
                System.out.println("Formato del c.postal erróneo");
                cPostal = 0;
            }
        }
        return cPostal;
    }
    private String pedirDNI(ArrayList<Usuario> usuarios, File fich) throws FileNotFoundException {
        String dni = null;
        boolean ok = false;
        while (dni == null) {
            dni = Llamar.lerString("DNI:");
            if (dni.length() == 9) {
                String numerosStr = dni.substring(0, 8);
                String letra = dni.substring(8);
                int numeros;
                try {
                    numeros = Integer.parseInt(numerosStr);
                    if (Character.isLetter(letra.charAt(0))) {
                        letra = letra.toUpperCase();
                        int resto = numeros % 23;
                        switch (resto) {
                            case 0:
                                if (!letra.equals("T")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 1:
                                if (!letra.equals("R")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 2:
                                if (!letra.equals("W")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 3:
                                if (!letra.equals("A")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 4:
                                if (!letra.equals("G")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 5:
                                if (!letra.equals("M")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 6:
                                if (!letra.equals("Y")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 7:
                                if (!letra.equals("F")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 8:
                                if (!letra.equals("P")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 9:
                                if (!letra.equals("D")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 10:
                                if (!letra.equals("X")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 11:
                                if (!letra.equals("B")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 12:
                                if (!letra.equals("N")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 13:
                                if (!letra.equals("J")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 14:
                                if (!letra.equals("Z")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 15:
                                if (!letra.equals("S")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 16:
                                if (!letra.equals("Q")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 17:
                                if (!letra.equals("V")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 18:
                                if (!letra.equals("H")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 19:
                                if (!letra.equals("L")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 20:
                                if (!letra.equals("C")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 21:
                                if (!letra.equals("K")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            case 22:
                                if (!letra.equals("E")) {
                                    System.out.println("El DNI introducido es falso");
                                    dni = null;
                                } else {
                                    ok = true;
                                }
                                break;
                            default:
                                break;
                        }
                    } else {
                        System.out.println("Formato del DNI incorrecto");
                        dni = null;
                    }
                } catch (NumberFormatException nfex) {
                    System.out.println("Formato del DNI incorrecto");
                    dni = null;
                }
            } else {
                System.out.println("Tamaño del DNI erróneo");
                dni = null;
            }
            //verificar que el dni no se encuentre ya en la base de datos
            if (ok == true) {
                for (Usuario usr : usuarios) {
                    if (usr.getDni().equals(dni)) {
                        System.out.println("El DNI ya se encuentra en la base de datos");
                        dni = null;
                        break;
                    }
                }
            }
        }
        return dni;
    }
}


