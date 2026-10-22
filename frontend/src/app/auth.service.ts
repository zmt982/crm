import { Injectable } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { authConfig } from './auth.config';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private oauthService: OAuthService) {
    this.oauthService.configure(authConfig);
    this.oauthService.loadDiscoveryDocumentAndTryLogin();
  }

  login()      { this.oauthService.initLoginFlow(); }
  logout()     { this.oauthService.logOut(); }
  isLoggedIn() { return this.oauthService.hasValidAccessToken(); }
  get token()  { return this.oauthService.getAccessToken(); }
}
