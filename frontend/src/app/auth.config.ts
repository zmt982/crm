import { AuthConfig } from 'angular-oauth2-oidc';

export const authConfig: AuthConfig = {
  issuer: 'http://localhost:8080/realms/crm-realm',
  redirectUri: window.location.origin,
  clientId: 'angular-client',
  responseType: 'code',
  scope: 'openid profile email',
  requireHttps: false,
  strictDiscoveryDocumentValidation: false, // Важно для локальной разработки!
};
