import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductListComponent } from './components/product-list/product-list';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, ProductListComponent],
  template: `
    <div style="padding: 20px;">
      <button *ngIf="!authService.isLoggedIn()" (click)="authService.login()">Войти через Keycloak</button>
      <button *ngIf="authService.isLoggedIn()" (click)="authService.logout()">Выйти</button>

      <hr>

      <!-- Показываем список товаров только если залогинен -->
      <app-product-list *ngIf="authService.isLoggedIn()"></app-product-list>
    </div>
  `
})
export class App {
  constructor(public authService: AuthService) {}
}
