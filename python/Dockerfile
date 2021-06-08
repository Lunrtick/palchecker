FROM python:3.9-slim
WORKDIR /app
COPY epistemic_model_checker/ ./epistemic_model_checker/
COPY requirements.txt setup.py ./
RUN pip install -r requirements.txt
RUN pip install --use-feature=in-tree-build .
ENTRYPOINT [ "python", "epistemic_model_checker/examples/muddy_children.py" ]
