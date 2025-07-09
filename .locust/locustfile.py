from locust import HttpUser, task, between
import random

class VotacaoCompleta(HttpUser):
    wait_time = between(0.5, 1)

    def gerar_cpf_aleatorio(self):
        return str(random.randint(10**10, 10**11 - 1))

    def gerar_resposta_aleatoria(self):
        return random.choice(["SIM", "NAO"])
    
    @task
    def votar_com_associado(self):
        # 1. Cadastra associado
        response_associado = self.client.post("/v1/associados", json={
            "cpf": self.gerar_cpf_aleatorio(),
            "nome": "Teste",
            "email": f"teste{random.randint(1, 10000)}@gmail.com"
        })

        if response_associado.status_code != 201:
            print("Falha ao cadastrar associado")
            return

        id_associado = response_associado.json().get("id")
        print(f"[DEBUG] ID do associado retornado: {id_associado}")

        resposta = self.gerar_resposta_aleatoria()
        
        payload = {
            "idAssociado": id_associado,
            "idSessaoVotacao": 1,
            "respostaVoto": resposta
        }

        response_voto = self.client.post("/v1/votos", json=payload)

        if response_voto.status_code != 201:
            try:
                erro = response_voto.json()
                print(f"Erro ao registrar voto: {erro}")
            except Exception:
                print(f"Erro ao registrar voto - resposta não JSON: {response_voto.text}")