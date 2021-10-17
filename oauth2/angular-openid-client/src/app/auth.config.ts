import { AuthConfig } from 'angular-oauth2-oidc';

export const authCodeFlowConfig: AuthConfig = {
  issuer: 'http://localhost:8080',
  redirectUri: window.location.origin,
  clientId: 'messaging-client',
  dummyClientSecret: 'secret',
  responseType: 'code',
  scope: 'openid profile email',
  showDebugInformation: true,
  timeoutFactor: 0.01,
};