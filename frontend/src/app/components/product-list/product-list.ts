import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService, Product } from '../../services/product';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-list.html'
})
export class ProductListComponent {
  searchId: number | null = 1;
  product: Product | null = null;

  constructor(
    private productService: ProductService,
    private cdr: ChangeDetectorRef // <-- Добавили для мгновенного обновления
  ) {}

  loadProduct() {
    if (!this.searchId) return;

    this.productService.getProductById(this.searchId).subscribe({
      next: (data) => {
        this.product = data;
        this.cdr.detectChanges(); // <-- Принудительно обновляем экран
      },
      error: (err) => {
        this.product = null; // Если ошибка — очищаем экран
        this.cdr.detectChanges();
        alert('Ошибка: ' + err.message);
      }
    });
  }
}
