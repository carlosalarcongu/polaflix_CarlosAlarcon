import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PolaflixService } from '../polaflix.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-entrada',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './entrada.component.html',
  styleUrls: ['./entrada.component.css']
})
export class EntradaComponent {
  username: string = '';
  error: string = '';

  constructor(private polaflixService: PolaflixService, private router: Router) {}

  entrar() {
    if (!this.username.trim()) return;
    
    this.polaflixService.entrar(this.username).subscribe({
      next: () => {
        sessionStorage.setItem('usuario', this.username);
        this.router.navigate(['/inicio']); 
      },
      error: () => {
        this.error = 'El usuario no existe.';
      }
    });
  }
}