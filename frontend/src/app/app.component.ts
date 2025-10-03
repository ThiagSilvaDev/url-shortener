import {Component, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {UrlService} from './url.service';
import {HttpClientModule} from '@angular/common/http';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule, HttpClientModule, NgIf],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  longUrl = '';
  shortUrl = signal<string | null>(null);
  error = signal<string | null>(null);
  loading = signal(false);

  constructor(private svc: UrlService) {
  }

  submit() {
    if (!this.longUrl) return;
    this.shortUrl.set(null);
    this.error.set(null);
    this.loading.set(true);
    this.svc.shorten(this.longUrl).subscribe({
      next: r => {
        this.shortUrl.set(r.shortUrl);
        this.loading.set(false);
      },
      error: err => {
        this.error.set(err?.error?.message || 'Request failed');
        this.loading.set(false);
      }
    });
  }
}
