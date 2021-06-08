FROM python:3.9-slim
WORKDIR /app
COPY epistemic_model_checker/ ./epistemic_model_checker/
COPY requirements.txt setup.py ./
RUN pip install -r requirements.txt
RUN pip install --use-feature=in-tree-build .
COPY hyperfine /usr/bin/hyperfine
ENTRYPOINT ["hyperfine", "--warmup", "1", "-m", "2", "-M", "50", "--export-json", "/var/results/mc-python.json", "--parameter-scan", "num_children", "3", "8", "python epistemic_model_checker/examples/muddy_children.py {num_children} 1"]
