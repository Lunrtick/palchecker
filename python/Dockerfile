FROM python:3.9-slim
WORKDIR /app
COPY epistemic_model_checker/ ./epistemic_model_checker/
COPY requirements.txt setup.py ./
RUN pip install -r requirements.txt
RUN pip install --use-feature=in-tree-build .
COPY hyperfine /usr/bin/hyperfine
ENV MIN_CHILDREN=3
ENV MAX_CHILDREN=8
ENV RESULTS_FILENAME="mc-python"
ENTRYPOINT hyperfine --warmup 1 -m 2 -M 50 --export-json /var/results/$RESULTS_FILENAME.json --parameter-scan num_children $MIN_CHILDREN $MAX_CHILDREN "python epistemic_model_checker/examples/muddy_children.py {num_children} 1"
