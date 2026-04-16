package br.com.aws_demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Bem-vindo ao MeuSite Java! " + LocalDateTime.now();
    }        
 
    @GetMapping("/cpu-test")
    public String cpuTest() {
        long end = System.currentTimeMillis() + 60_000;
        while (System.currentTimeMillis() < end) {
            double x = Math.sqrt(Math.random() * 1000); 
        }
        return "CPU teste finalizado em " + LocalDateTime.now();
    }
   
    @GetMapping("/memory-test")
    public String memoryTest() {
        List<byte[]> memoryEater = new ArrayList<>();
        long end = System.currentTimeMillis() + 60_000;
        try {
            while (System.currentTimeMillis() < end) {
                memoryEater.add(new byte[10_000_000]);
                Thread.sleep(500);
            }
        } catch (OutOfMemoryError e) {
            return "Estourou memória! " + LocalDateTime.now();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Memory teste finalizado " + LocalDateTime.now();
    }
    
    @GetMapping("/stress-test")
    public String stressTest() {
        List<byte[]> memory = new ArrayList<>();
        long end = System.currentTimeMillis() + 60_000;
        try {
            while (System.currentTimeMillis() < end) {               
                double x = Math.sqrt(Math.random() * 1000);               
                memory.add(new byte[5_000_000]);
                Thread.sleep(200);
            }
        } catch (OutOfMemoryError e) {
            return "Estourou memória no stress test!";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Stress teste finalizado " + LocalDateTime.now();
    }
}