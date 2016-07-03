/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holodniysvitanok.cmd;

import com.holodniysvitanok.cmd.CommandAndParam;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author Admin
 */
public class CommandLine {

    private final String alert = "этой комманде нужен параметр";
    private final String secret = "tigro";

    private File file;

    public CommandLine(String args[]) {
        if (args.length >= 1) {
            file = new File(args[0]);
            if (args.length >= 2) {
                helloKitty(args[1]);
            }
        } else {
            file = new File(System.getProperty("user.dir"));
        }
    }

    public CommandLine() {
        file = new File(System.getProperty("user.dir"));
    }

    public void listenerCommand() throws IOException {
        help();
        try (BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print(">> " + file.getAbsolutePath() + ">");
                action(file, readCommandLine(bReader.readLine()));
            }
        }
    }

    private CommandAndParam readCommandLine(String line) throws IOException {
        if (line.equals("")) {
            return null;
        }
        String temp[] = line.split(" ");
        CommandAndParam cap = null;
        try {
            if (temp.length == 2) {
                cap = new CommandAndParam(temp[0], temp[1], null);
            } else if (temp.length == 3) {
                cap = new CommandAndParam(temp[0], temp[1], temp[2]);
            } else {
                cap = new CommandAndParam(temp[0], null, null);
            }
        } catch (Exception ex) {
            System.out.println("такой комманды нет или параметры введены не верно, читай HeLp");
        }
        return cap;
    }

    private void action(File file, CommandAndParam cap) {
        if (cap != null) {
            switch (cap.getCom()) {
                case CD:
                    cd(file, cap);
                    break;
                case MKDIR:
                    mkdir(file, cap);
                    break;
                case DIR:
                    dir(file);
                    break;
                case HELP:
                    help();
                    break;
                case DIRFILTER:
                    getAllDirectoriesByFilter(file, cap);
                    break;
                case FILEFILTER:
                    getAllFileByFilter(file, cap);
                    break;
                case OPEN:
                    openTextFile(file, cap);
                    break;
                case DEL:
                    del(file, cap);
                    break;
                case COPY:
                    copy(file, cap);
                    break;
                case RENAME:
                    renameTo(file, cap);
                    break;
                case EXIT:
                    exit();
            }
        }
    }

    /**
     * реализация комманд
     */
    private void cd(File file, CommandAndParam cap) {
        if (cap.getParam() == null) {
            System.out.println(alert);
            return;
        } else if (cap.getParam().equals("..") || cap.getParam().equals("../")) {

            if (file.getAbsoluteFile().getParentFile() == null) {
                System.out.println("дальше только космос");
                return;
            }
            this.file = file.getAbsoluteFile().getParentFile();
            return;
        }

        File newPath = new File(cap.getParam());
        if (newPath.exists()) {
            if (newPath.isFile()) {
                System.out.println("это не каталог, а файл.");
                return;
            }
            this.file = newPath;
            return;
        } else {
            System.out.println("системе не удаёться найти указанный путь");
        }
    }

    private void dir(File file) {
        File[] listFiles = file.listFiles();
        for (File currentFile : listFiles) {
            System.out.print((currentFile.isDirectory()) ? "<DIR>" : "<FILE>");
            System.out.print("\t\t" + currentFile.getName());
            System.out.print("\n");
        }
    }

    private void exit() {
        System.out.println("bay bay");
        System.exit(0);
    }

    private void mkdir(File file, CommandAndParam cap) {

        if (cap.getParam() == null) {
            System.out.println(alert);
            return;
        }
        File newFile = null;
        if (cap.getParam().contains(".")) {
            newFile = new File(file.getAbsolutePath() + File.separatorChar + cap.getParam());
            try {
                newFile.createNewFile();
            } catch (IOException ex) {
                System.out.println("что-то не так");
            }
            return;
        }
        newFile = new File(file.getAbsolutePath() + File.separatorChar + cap.getParam());
        if (!newFile.mkdir()) {
            System.out.println("такой файл уже существует");
        }
    }

    private void openTextFile(File file, CommandAndParam cap) {

        if (cap.getParam() == null || !cap.getParam().contains(".txt")) { // супер свойвство двойного или
            System.out.println("я разрешаю открывать только txt файлы ");
            return;
        }
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath() + File.separatorChar + cap.getParam()), "Cp1251"));
            String string = null;
            while ((string = bReader.readLine()) != null) {
                System.out.println(string);
            }

        } catch (FileNotFoundException ex) {
            System.out.println("такого файлика нету ;(");
        } catch (IOException ex) {
        }

    }

    private void del(File file, CommandAndParam cap) {
        if (cap.getParam() == null) {
            System.out.println(alert);
            return;
        }
        File deleteFile = new File(file.getAbsolutePath() + File.separatorChar + cap.getParam());
        if (!deleteFile.delete()) {
            System.out.println("удалить не вышло, возможно нет такого фала (каталога) или каталог не пуст");
        }
    }

    private void copy(File file, CommandAndParam cap) {
        if (cap.getParam() == null) {
            System.out.println(alert);
            return;
        }
        try (BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath() + File.separatorChar + cap.getParam()), "Cp1251"));
                BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath() + File.separatorChar + cap.getParam2()), "Cp1251"))) {
            String string = null;
            while ((string = bReader.readLine()) != null) {
                bWriter.write(string);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("такого файлика нету ;(");
        } catch (IOException ex) {
        }

    }

    private void help() {
        final String message
                = "\n***********************************************************\n"
                + "При написании комманд - регистр не важен\n"
                + "Какие мы комманды поддерживаем ? А вот какие ...\n"
                + "DIR\t\tОтображает содержимое каталога\n"
                + "DIRFILTER\tОтображает или переходит по каталогам с помощью фильтра\n\t\t(только каталоги)\n"
                + "FILEFILTER\tОтобразит файлы в каталоге спомощью фильтра\n\t\t(только файлы)\n"
                + "CD\t\tСмена текущего каталога или диска\n"
                + "MKDIR\t\tСоздаёт папку или файл\n"
                + "HELP\t\tСправка\n"
                + "EXIT\t\tВыход\n"
                + "OPEN\t\tОткрывает текстовый документ\n"
                + "COPY\t\tКопировать документы (принимает два параметра 1-откуда, 2-куда)\n"
                + "DEL\t\tУдаляет папку или файл\n"
                + "*************************************************************\n";
        System.out.print(message);
    }

    private void helloKitty(String password) {

        final String cat = "───────────────────────────────────────\n"
                + "───▐▀▄───────▄▀▌───▄▄▄▄▄▄▄─────────────\n"
                + "───▌▒▒▀▄▄▄▄▄▀▒▒▐▄▀▀▒██▒██▒▀▀▄──────────\n"
                + "──▐▒▒▒▒▀▒▀▒▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀▄────────\n"
                + "──▌▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▄▒▒▒▒▒▒▒▒▒▒▒▒▀▄──────\n"
                + "▀█▒▒▒█▌▒▒█▒▒▐█▒▒▒▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▌─────\n"
                + "▀▌▒▒▒▒▒▒▀▒▀▒▒▒▒▒▒▀▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐───▄▄\n"
                + "▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▌▄█▒█\n"
                + "▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█▒█▀─\n"
                + "▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█▀───\n"
                + "▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▌────\n"
                + "─▌▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐─────\n"
                + "─▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▌─────\n"
                + "──▌▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▐──────\n"
                + "──▐▄▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▄▌──────\n"
                + "────▀▄▄▀▀▀▀▀▄▄▀▀▀▀▀▀▀▄▄▀▀▀▀▀▄▄▀────────\n"
                + "\t\t(я тигрЪ)\n";

        //эта кыська в консоле гараздо круче смотрится чем в иде      
        if (password.equals(secret)) {
            System.out.println(cat);
        }
    }

    private void renameTo(File file, CommandAndParam cap) {
        if (cap.getParam() == null) {
            return;
        }
        File old = new File(file.getAbsolutePath() + File.separatorChar + cap.getParam());
        old.renameTo(new File(file.getAbsolutePath() + File.separatorChar + cap.getParam2()));
    }

    private void getAllFileByFilter(File file, CommandAndParam cap) {
        if (cap.getParam() == null) {
            System.out.println(alert);
            return;
        }
        File filterFile[] = file.listFiles(new MyFilterForFile(cap.getParam()));
        if (filterFile.length == 1) {
            if (filterFile[0].isDirectory()) {
                return;
            }
            System.out.println(filterFile[0].getName());
            return;
        }

        for (File files : filterFile) {
            System.out.println(files.getName());
        }

    }

    private void getAllDirectoriesByFilter(File file, CommandAndParam cap) {
        if (cap.getParam() == null) {
            System.out.println(alert);
            return;
        }
        File filterFile[] = file.listFiles(new MyFilterForFolder(cap.getParam()));

        if (filterFile.length == 1) {
            if (filterFile[0].getName().contains(".")) {
                return;
            }
            this.file = filterFile[0];
            return;
        }

        for (File files : filterFile) {
            System.out.println(files.getName());
        }

    }
}
