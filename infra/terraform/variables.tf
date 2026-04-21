variable "project_id" {
  description = "L'ID de votre projet Google Cloud"
  type        = string
  default     = "scilib-devops" 
}

variable "region" {
  description = "Région GCP"
  type        = string
  default     = "europe-west1"
}

variable "zone" {
  description = "Zone GCP"
  type        = string
  default     = "europe-west1-b"
}
