import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {OAuthService} from 'angular-oauth2-oidc';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const oauthService = inject(OAuthService);
  const token = oauthService.getAccessToken();

  // Если токен есть, клонируем запрос и добавляем заголовок
  if (token) {
    const cloned = req.clone({
      setHeaders: {Authorization: `Bearer ${token}`}
    });
    return next(cloned);
  }
  return next(req);
};
