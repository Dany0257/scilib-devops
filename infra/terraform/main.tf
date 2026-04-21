provider "google" {
  project = var.project_id
  region  = var.region
  zone    = var.zone
  credentials = file("credentials.json")
}

# Instance Compute Engine (VM)
resource "google_compute_instance" "scilib_vm" {
  name         = "scilib-instance"
  machine_type = "e2-micro" # Standard actuel gratuit (Free Tier)

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-11"
    }
  }

  network_interface {
    network = "default"
    access_config {
      # Nécessaire pour avoir une IP publique
    }
  }

  metadata = {
    ssh-keys = "debian:${file("~/.ssh/id_rsa.pub")}"
  }
}

# Règle de Pare-feu pour autoriser le SSH
resource "google_compute_firewall" "ssh_firewall" {
  name    = "allow-ssh"
  network = "default"

  allow {
    protocol = "tcp"
    ports    = ["22"]
  }

  source_ranges = ["0.0.0.0/0"]
}

output "instance_ip" {
  value = google_compute_instance.scilib_vm.network_interface[0].access_config[0].nat_ip
}
