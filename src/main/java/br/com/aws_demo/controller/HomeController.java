package br.com.aws_demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	 @Autowired
	 private ExecutorService executor;

    @GetMapping("/")
    public String home() {
        return "Bem-vindo ao MeuSite Java! " + LocalDateTime.now();
    }        
 
    @GetMapping("/cpu-test")
    public String cpuTest() {
        executor.submit(() -> {
            long end = System.currentTimeMillis() + 60_000;
            while (System.currentTimeMillis() < end) {
                double x = Math.sqrt(Math.random() * 1000);
            }
        });
        return "Teste de CPU iniciado!";
    }
    
    @GetMapping("/memory-test")
    public String memoryTest() {
        executor.submit(() -> {
            List<byte[]> memory = new ArrayList<>();
            long end = System.currentTimeMillis() + 60_000;
            try {
                while (System.currentTimeMillis() < end) {
                    memory.add(new byte[10_000_000]); // ~10MB
                    Thread.sleep(500);
                }
            } catch (OutOfMemoryError e) {
                System.out.println("Estourou memória!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        return "Teste de memória iniciado!";
    }
    
    @GetMapping("/stress-test")
    public String stressTest() {
        executor.submit(() -> {
            List<byte[]> memory = new ArrayList<>();
            long end = System.currentTimeMillis() + 60_000;
            try {
                while (System.currentTimeMillis() < end) {
                    double x = Math.sqrt(Math.random() * 1000);
                    memory.add(new byte[5_000_000]); // ~5MB
                    Thread.sleep(200);
                }
            } catch (OutOfMemoryError e) {
                System.out.println("OOM no stress test!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        return "Teste de Stress iniciado!";
    }
}