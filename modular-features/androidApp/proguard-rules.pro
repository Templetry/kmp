# R8 rules for the release build of androidApp (isMinifyEnabled = true in the
# kmpbase.android.app convention). The libraries in use ship their own consumer
# rules; add app-specific keeps here.

# Tink (pulled in by androidx.security:security-crypto) references Error Prone
# annotations that are compile-time only.
-dontwarn com.google.errorprone.annotations.**
