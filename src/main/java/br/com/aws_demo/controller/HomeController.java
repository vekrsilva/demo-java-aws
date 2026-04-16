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

//    @GetMapping("/")
//    public String home() {
//        return "Bem-vindo ao MeuSite Java! " + LocalDateTime.now();
//    }   
	 
	 @GetMapping("/")
	 public String home() {
	     return """
	         <!DOCTYPE html>
	         <html lang="pt-BR">
	         <head>
	             <meta charset="UTF-8">
	             <title>Monitoramento ECS</title>
	             <style>
	                 body {
	                     font-family: Arial, sans-serif;
	                     background: #0f172a;
	                     color: #e2e8f0;
	                     text-align: center;
	                     padding: 40px;
	                 }
	                 h1 {
	                     margin-bottom: 10px;
	                 }
	                 .container {
	                     margin-top: 30px;
	                 }
	                 .btn {
	                     display: inline-block;
	                     margin: 10px;
	                     padding: 15px 25px;
	                     font-size: 16px;
	                     border-radius: 8px;
	                     text-decoration: none;
	                     color: white;
	                     transition: 0.2s;
	                 }
	                 .cpu { background: #ef4444; }
	                 .memory { background: #3b82f6; }
	                 .stress { background: #22c55e; }

	                 .btn:hover {
	                     transform: scale(1.05);
	                     opacity: 0.9;
	                 }

	                 .footer {
	                     margin-top: 40px;
	                     font-size: 14px;
	                     color: #94a3b8;
	                 }
	             </style>
	         </head>
	         <body>

	             <h1>🚀 MeuSite - Teste ECS Fargate</h1>

	             <div class="container">
	                 <a class="btn cpu" href="/cpu-test">🔥 Teste de CPU</a>
	                 <a class="btn memory" href="/memory-test">🧠 Teste de Memória</a>
	                 <a class="btn stress" href="/stress-test">⚡ Stress Test</a>
	             </div>

	             <div class="footer">
	                 <p>ALB: http://minha-app-alb-1248273371.us-east-1.elb.amazonaws.com</p>
	             </div>

	         </body>
	         </html>
	         """;
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