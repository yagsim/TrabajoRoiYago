/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectura;

import Escritura.Escritura;
import Usuario.Usuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import libreriaroiyago.Llamar;

/**
 *
 * @author yagos
 */
public class Lectura {
    public static void vertirFicheroEnArrayList(ArrayList<Usuario> usuarios, File fich) throws FileNotFoundException {
        usuarios.clear();
        Scanner scan = new Scanner(fich);
        String str;
        while (scan.hasNextLine()) {
            str = scan.nextLine();
            String[] lista = str.split(",");
            usuarios.add(new Usuario(lista[0], lista[1], Integer.parseInt(lista[2]), lista[3], lista[4], Integer.parseInt(lista[5])));
        }
        scan.close();
    }

    public void visualizar(ArrayList<Usuario> usuarios, File nombreFichero) {
        try {
            Lectura.vertirFicheroEnArrayList(usuarios, nombreFichero);

            int var = 0;
            while (var != 4) {
                try {
                    var = Llamar.lerInt("1. Filtrar por Nombre \n2. Filtrar por DNI \n3. Filtrar por Edad \n4. Volver");
                } catch (NumberFormatException ex) {
                    System.out.println("error6: "+ex.toString());
                }
                switch (var) {
                    case 1:
                        ordenarPorNombre(usuarios);
                        break;
                    case 2:
                        ordenarPorNIF(usuarios);
                        break;
                    case 3:
                        ordenarPorFechaN(usuarios);
                        break;
                    case 4:
                        break;
                    default:
                        break;
                }
                if (var != 4) {
                    ArrayList<Usuario>[] mayoresMenores = separarMayoresYMenores(usuarios);
                    ArrayList<Usuario> mayores = mayoresMenores[0];
                    ArrayList<Usuario> menores = mayoresMenores[1];

                    System.out.println();
                    if (mayores.size() > 0) {
                        System.out.println("MAYORES DE EDAD\n");
                        for (int i = 0; i < mayores.size(); i++) {
                            System.out.printf("%-20.18s", mayores.get(i).getNombre());
                            System.out.printf("%-12s", mayores.get(i).getDni());
                            System.out.printf("%-12s", mayores.get(i).getFechaN());
                            System.out.printf("%-8d", mayores.get(i).getcPost());
                            System.out.printf("%-29.26s", mayores.get(i).getCorreo());
                            if (mayores.get(i).getTlf() != 0)
                                System.out.printf("%-11d", mayores.get(i).getTlf());
                            System.out.println();
                        }
                        System.out.println("------------------------------------------------------------------------------------------------------------\n");
                    }
                    if (menores.size() > 0) {
                        System.out.println("MENORES DE EDAD\n");
                        for (int i = 0; i < menores.size(); i++) {
                            System.out.printf("%-20.18s", menores.get(i).getNombre());
                            System.out.printf("%-12s", menores.get(i).getDni());
                            System.out.printf("%-12s", menores.get(i).getFechaN());
                            System.out.printf("%-8d", menores.get(i).getcPost());
                            System.out.printf("%-29.26s", menores.get(i).getCorreo());
                            if (menores.get(i).getTlf() != 0)
                                System.out.printf("%-11d", menores.get(i).getTlf());
                            System.out.println();
                        }
                        System.out.println("------------------------------------------------------------------------------------------------------------\n");
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("error (Ficheiro non atopado)" + ex.toString());
        }
    }
    private ArrayList<Usuario>[] separarMayoresYMenores(ArrayList<Usuario> usuarios) {
        ArrayList<Usuario>[] mayoresMenores = new ArrayList[2];
        mayoresMenores[0] = new ArrayList<>();
        mayoresMenores[1] = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).obtenerEdad() >= 18) {
                mayoresMenores[0].add(usuarios.get(i));
            } else {
                mayoresMenores[1].add(usuarios.get(i));
            }
        }
        return  mayoresMenores;
    }
    private void ordenarPorNombre(ArrayList<Usuario> usuarios) {
        Usuario copia;
        for (int i = 0; i < usuarios.size(); i++) {
            for (int e = i; e < usuarios.size(); e++) {
                if (usuarios.get(i).compareTo(usuarios.get(e)) > 0) {
                    copia = usuarios.get(i);
                    usuarios.set(i, usuarios.get(e));
                    usuarios.set(e, copia);
                }
            }
        }
    }
    private void ordenarPorFechaN(ArrayList<Usuario> usuarios) {
        Usuario copia;
        for (int i = 0; i < usuarios.size(); i++) {
            for (int e = i; e < usuarios.size(); e++) {
                if (usuarios.get(i).compareToByDate(usuarios.get(e)) > 0) {
                    copia = usuarios.get(i);
                    usuarios.set(i, usuarios.get(e));
                    usuarios.set(e, copia);
                }
            }
        }
    }
    private void ordenarPorNIF(ArrayList<Usuario> usuarios) {
        Usuario copia;
        for (int i = 0; i < usuarios.size(); i++) {
            for (int e = i; e < usuarios.size(); e++) {
                if (usuarios.get(i).compareToByID(usuarios.get(e)) > 0) {
                    copia = usuarios.get(i);
                    usuarios.set(i, usuarios.get(e));
                    usuarios.set(e, copia);
                }
            }
        }
    }
    public void buscar(ArrayList<Usuario> usuarios, File nombreFichero) throws IOException {
        vertirFicheroEnArrayList(usuarios, nombreFichero);
        String DNI = Llamar.lerString("DNI a buscar");
        String nombre = null;
        boolean encontrado = false;
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getDni().equals(DNI)) {
                nombre = usuarios.get(i).getNombre();
                encontrado = true;
                break;
            }
        }
        if (encontrado == true) {
            int opcion = 0;
            while (opcion != 4 && opcion != 3) {
                try {
                    opcion = Integer.parseInt(JOptionPane.showInputDialog(nombre.toUpperCase()+" - "+DNI+"\n1. Mostrar datos \n2. Modificar  \n3. Eliminar \n4. Atrás"));
                } catch (NumberFormatException ex) {
                    System.out.println("error6: "+ex.toString());
                }
                switch (opcion) {
                    case 1:
                        for (int i = 0; i < usuarios.size(); i++) {
                            if (usuarios.get(i).getDni().equals(DNI)) {
                                System.out.println();
                                System.out.printf("%-20.18s", usuarios.get(i).getNombre());
                                System.out.printf("%-12s", usuarios.get(i).getDni());
                                System.out.printf("%-12s", usuarios.get(i).getFechaN());
                                System.out.printf("%-8d", usuarios.get(i).getcPost());
                                System.out.printf("%-29.26s", usuarios.get(i).getCorreo());
                                if (usuarios.get(i).getTlf() != 0)
                                    System.out.printf("%-11d", usuarios.get(i).getTlf());
                                System.out.println("\n------------------------------------------------------------------------------------------------------------\n");
                                break;
                            }
                        }
                        break;
                    case 2:
                        Escritura.modificar(usuarios, nombreFichero, DNI);
                        break;
                    case 3:
                        Escritura.eliminar(usuarios, nombreFichero, DNI);
                        break;
                    case 4:
                        break;
                    default:
                        break;
                }
            }

        } else {
            System.out.println("Usuario no encontrado");
        }

    }
}
